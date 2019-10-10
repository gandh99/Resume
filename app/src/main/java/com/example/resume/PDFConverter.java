package com.example.resume;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFConverter {
  private final String YOUR_FOLDER_NAME = "hello";
  private View view;
  private Activity activity;

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public PDFConverter(Activity activity, View view) {
    this.activity = activity;
    this.view = view;
    Toast.makeText(activity, getSaveImageFilePath(), Toast.LENGTH_SHORT).show();
  }

  String getSaveImageFilePath() {
    File mediaStorageDir = new File(
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), YOUR_FOLDER_NAME);
    // Create a storage directory if it does not exist
    if (!mediaStorageDir.exists()) {
      if (!mediaStorageDir.mkdirs()) {
        Log.d(YOUR_FOLDER_NAME, "Failed to create directory");
      }
    }

    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageName = "IMG_" + timeStamp + ".jpg";

    String selectedOutputPath = mediaStorageDir.getPath() + File.separator + imageName;
    Log.d(YOUR_FOLDER_NAME, "selected camera path " + selectedOutputPath);

    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();

    Bitmap bitmap = Bitmap.createBitmap( view.getLayoutParams().width, view.getLayoutParams().height, Bitmap.Config.ARGB_8888);

    int maxSize = 1080;

    int bWidth = bitmap.getWidth();
    int bHeight = bitmap.getHeight();

    if (bWidth > bHeight) {
      int imageHeight = (int) Math.abs(maxSize * ((float)bitmap.getWidth() / (float) bitmap.getHeight()));
      bitmap = Bitmap.createScaledBitmap(bitmap, maxSize, imageHeight, true);
    } else {
      int imageWidth = (int) Math.abs(maxSize * ((float)bitmap.getWidth() / (float) bitmap.getHeight()));
      bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, maxSize, true);
    }
    view.setDrawingCacheEnabled(false);
    view.destroyDrawingCache();

    OutputStream fOut = null;
    try {
      File file = new File(selectedOutputPath);
      fOut = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
      fOut.flush();
      fOut.close();
      MediaStore.Images.Media.insertImage(activity.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return selectedOutputPath;
  }
}
