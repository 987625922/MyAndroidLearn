package com.wind.androidlearn.img.imgcache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCacheUtils {

    private LruCache<String, Bitmap> memoryCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;//获取允许最大内存的8分之1
        memoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount / 1024;
            }
        };

    }

    /*
     *
     * 从内存中获取bitmap
     * */
    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = memoryCache.get(url);
        return bitmap;
    }

    /*
     * 把bitmap设置在内存中
     * */
    public void setBitmapFormMemory(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
    }



}
