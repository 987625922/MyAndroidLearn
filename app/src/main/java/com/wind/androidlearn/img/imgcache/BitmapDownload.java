package com.wind.androidlearn.img.imgcache;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface BitmapDownload {

    Bitmap load(ImageView img, String url);

}
