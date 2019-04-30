package com.wind.androidlearn.img.imgcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetBitmapDownloadUtils {

    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private BitmapTask bitmapTask;

    public NetBitmapDownloadUtils() {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
    }

    public MemoryCacheUtils getMemoryCacheUtils() {
        return memoryCacheUtils;
    }

    public LocalCacheUtils getLocalCacheUtils() {
        return localCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        bitmapTask = new BitmapTask();
        bitmapTask.execute(imageView, url);
    }


    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        //耗时操作，子线程
        @Override
        protected Bitmap doInBackground(Object... objects) {
            imageView = (ImageView) objects[0];
            url = (String) objects[1];
            return downLoadBitmap(url);
        }

        //更新进度，在主线程
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                memoryCacheUtils.setBitmapFormMemory(url, bitmap);
                try {
                    localCacheUtils.setBitmapFromLocal(url, bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private Bitmap downLoadBitmap(String url) {
        HttpURLConnection conn = null;
        Bitmap bitmap = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return bitmap;
    }


}
