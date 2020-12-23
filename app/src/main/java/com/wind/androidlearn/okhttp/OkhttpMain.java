package com.wind.androidlearn.okhttp;

import java.io.IOException;
import java.util.List;

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
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
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
                System.out.println(TAG + "onFailure: "+ response.body().string());
            }
        });
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(TAG + "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(TAG + "onFailure: "+ response.body().string());
            }
        });
    }

}
