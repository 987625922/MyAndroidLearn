package com.wind.androidlearn.img.imgcache;

import android.graphics.Bitmap;
import android.widget.ImageView;


public class BitmapDownloadUtil implements BitmapDownload {

    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private NetBitmapDownloadUtils netBitmapDownloadUtils;

    private BitmapDownloadUtil() {
        netBitmapDownloadUtils = new NetBitmapDownloadUtils();
        memoryCacheUtils = netBitmapDownloadUtils.getMemoryCacheUtils();
        localCacheUtils = netBitmapDownloadUtils.getLocalCacheUtils();

    }


    @Override
    public Bitmap load(ImageView img, String url) {
        Bitmap bitmap;
        bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null){
            img.setImageBitmap(bitmap);
            return bitmap;
        }
        bitmap = localCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null){
            img.setImageBitmap(bitmap);
            return bitmap;
        }
        netBitmapDownloadUtils.getBitmapFromNet(img,url);
        return null;
    }

    static class BitmapDownloadUntilHelper {
        private static BitmapDownloadUtil bdu = new BitmapDownloadUtil();
    }

    public static BitmapDownloadUtil getInstance() {
        return BitmapDownloadUntilHelper.bdu;
    }



}
