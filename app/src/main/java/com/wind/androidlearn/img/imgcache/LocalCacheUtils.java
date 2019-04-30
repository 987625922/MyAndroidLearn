package com.wind.androidlearn.img.imgcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.wind.androidlearn.bassis.Utils.MD5Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalCacheUtils {
    private static final String PATH =
            Environment.getDataDirectory().getParentFile() + "/webNews";
    /*
     * 获取本地bitmap
     * */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null;
        try {
            fileName = MD5Util.crypt(url);
            File file = new File(PATH, fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setBitmapFromLocal(String url, Bitmap bitmap) throws FileNotFoundException {
        String fileName = MD5Util.crypt(url);
        File file = new File(PATH, fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        try {
            fileOutputStream.write(bitmap.getRowBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
