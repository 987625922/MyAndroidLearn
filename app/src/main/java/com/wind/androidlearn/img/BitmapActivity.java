package com.wind.androidlearn.img;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.wyt.zdf.myapplication.R;

/**
 * 创建日期：2018/9/11 0011<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * 待做事情：
 */
public class BitmapActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.you_er_bj);
        BitmapUtils.getByteBitmap(bitmap);

    }
}
