package com.wind.androidlearn;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wyt.zdf.myapplication.R;

/**
 * 创建日期：2018/9/6 0006<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class TestActivity extends Activity  {
    private TextView textView;
    private String picName = "networkPic.jpg";
//    private NetworkImageGetter mImageGetter;
    String str = "<img src=\"http://image.shentong360.com/upload/img/20180906/0547198eeab47323beabc0205a456917.jpg\"><br>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.textView2);
        MyImageGetter myImageGetter = new MyImageGetter();
        textView.setText(Html.fromHtml(str, myImageGetter, null));

    }

    class MyDrawableWrapper extends BitmapDrawable {
        private Drawable drawable;
        MyDrawableWrapper() {
        }
        @Override
        public void draw(Canvas canvas) {
            if (drawable != null)
                drawable.draw(canvas);
        }
        public Drawable getDrawable() {
            return drawable;
        }
        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }

    class MyImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            MyDrawableWrapper myDrawable = new MyDrawableWrapper();
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            drawable.setBounds(
                    0,
                    0,
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            myDrawable.setDrawable(drawable);
            Glide.with(TestActivity.this)
                    .asBitmap()
                    .load(source)
                    .into(new BitmapTarget(myDrawable));
            return myDrawable;
        }
    }

    class BitmapTarget extends SimpleTarget<Bitmap> {
        private final MyDrawableWrapper myDrawable;
        public BitmapTarget(MyDrawableWrapper myDrawable) {
            this.myDrawable = myDrawable;
        }
        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
            Drawable drawable = new BitmapDrawable(getResources(), resource);
            //获取原图大小
            int width=drawable.getIntrinsicWidth() ;
            int height=drawable.getIntrinsicHeight();
            //自定义drawable的高宽, 缩放图片大小最好用matrix变化，可以保证图片不失真
            drawable.setBounds(0, 0, 500, 500);
            myDrawable.setBounds(0, 0, 500, 500);
            myDrawable.setDrawable(drawable);
            textView.setText(textView.getText());
            textView.invalidate();
        }
    }



}
