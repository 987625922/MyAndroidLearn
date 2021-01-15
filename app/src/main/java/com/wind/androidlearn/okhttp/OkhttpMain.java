package com.wind.androidlearn.okhttp;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/2 10:55
 */
class OkhttpMain {
    public static final String TAG = "okhttp源码解析";

    public static void main(String[] args) {
        //okhttp的缓存
        File cacheFile = new File("缓存文件的path", "cache");
        int cacheSeize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSeize);
        //缓存的设置
        final CacheControl.Builder builder = new CacheControl.Builder();
        //设置最大有效时间为100毫秒
        builder.maxAge(100, TimeUnit.MILLISECONDS);
        CacheControl cacheControl = builder.build();
        //开始网络请求
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();
        final Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .get() //默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(TAG + "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(TAG + "onFailure: " + response.body().string());
            }
        });
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(TAG + "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(TAG + "onFailure: " + response.body().string());
            }
        });
    }

    class mainActivity extends Activity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Glide.with(this).load("").into(new ImageView(this));
        }
    }
}
