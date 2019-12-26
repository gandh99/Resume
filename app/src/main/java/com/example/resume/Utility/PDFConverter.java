package com.example.resume.Utility;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PDFConverter {

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public static boolean createPdf(Bitmap b) {
    boolean success = false;
    PdfDocument document = new PdfDocument();
    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
    PdfDocument.Page page = document.startPage(pageInfo);

    Canvas canvas = page.getCanvas();
    Paint paint = new Paint();
    paint.setColor(Color.parseColor("#ffffff"));
    canvas.drawPaint(paint);
    Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

    paint.setColor(Color.BLUE);
    canvas.drawBitmap(bitmap, 0, 0, null);
    document.finishPage(page);

    final File folder = new File("/mnt/sdcard/Resume");
    File filePath = new File(folder, "myResume" + ".pdf");

    try {
      document.writeTo(new FileOutputStream(filePath));
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    // close the document
    document.close();

    return success;
  }
}
