package com.example.resume.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageConverter {

  public static File createImage(Context context, View drawView){
    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
      "Handcare");
    if (!pictureFileDir.exists()) {
      boolean isDirectoryCreated = pictureFileDir.mkdirs();
      if(!isDirectoryCreated)
        Log.i("ATG", "Can't create directory to save the image");
      return null;
    }
    String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
    File pictureFile = new File(filename);
    Bitmap bitmap = getScreenshotFromRecyclerView((RecyclerView) drawView);

    try {
      pictureFile.createNewFile();
      FileOutputStream oStream = new FileOutputStream(pictureFile);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
      oStream.flush();
      oStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      Log.i("TAG", "There was an issue saving the image.");
    }

    scanGallery(context, pictureFile.getAbsolutePath());
    return pictureFile;
  }

  public static Bitmap getBitmap(View drawView){
    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
      "Handcare");
    if (!pictureFileDir.exists()) {
      boolean isDirectoryCreated = pictureFileDir.mkdirs();
      if(!isDirectoryCreated)
        Log.i("ATG", "Can't create directory to save the image");
      return null;
    }
    String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
    File pictureFile = new File(filename);
    Bitmap bitmap = getScreenshotFromRecyclerView((RecyclerView) drawView);

    try {
      pictureFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
      Log.i("TAG", "There was an issue saving the image.");
    }

    return bitmap;
  }

  // Used for scanning gallery
  private static void scanGallery(Context cntx, String path) {
    try {
      MediaScannerConnection.scanFile(cntx, new String[] { path },null,
        new MediaScannerConnection.OnScanCompletedListener() {
        public void onScanCompleted(String path, Uri uri) {
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Bitmap getScreenshotFromRecyclerView(RecyclerView view) {
    RecyclerView.Adapter adapter = view.getAdapter();
    Bitmap bigBitmap = null;
    if (adapter != null) {
      int size = adapter.getItemCount();
      int height = 0;
      Paint paint = new Paint();
      int iHeight = 0;
      final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

      // Use 1/8th of the available memory for this memory cache.
      final int cacheSize = maxMemory / 8;
      LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
      for (int i = 0; i < size; i++) {
        RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
        adapter.onBindViewHolder(holder, i);
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
        holder.itemView.setDrawingCacheEnabled(true);
        holder.itemView.buildDrawingCache();
        Bitmap drawingCache = holder.itemView.getDrawingCache();
        if (drawingCache != null) {

          bitmaCache.put(String.valueOf(i), drawingCache);
        }
//                holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
        height += holder.itemView.getMeasuredHeight();
      }

      bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
      Canvas bigCanvas = new Canvas(bigBitmap);
      bigCanvas.drawColor(Color.WHITE);

      for (int i = 0; i < size; i++) {
        Bitmap bitmap = bitmaCache.get(String.valueOf(i));
        bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
        iHeight += bitmap.getHeight();
        bitmap.recycle();
      }

    }
    return bigBitmap;
  }
}
