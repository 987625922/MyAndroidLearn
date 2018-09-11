package com.wind.wind.myapplication.img;

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
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class BitmapActivity extends Activity{
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        imageView = findViewById(R.id.img_bitmap);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.you_er_bj);
        BitmapUtils.getByteBitmap(bitmap);

    }
}
