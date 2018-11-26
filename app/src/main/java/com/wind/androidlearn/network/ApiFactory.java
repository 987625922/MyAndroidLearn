package com.wind.androidlearn.network;


import com.wind.androidlearn.bassis.Utils.LogUtil;
import com.wind.androidlearn.network.converter.DecodeConverterFactory;
import com.wyt.zdf.myapplication.BuildConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api创建工厂类
 * HttpClient 和 Retrofit 的创建
 */
public final class ApiFactory {

    private final static int DEFAULT_TIMEOUT = 10;
    private static String TAG = "ApiFactory";
    private static ApiService apiService;
    private static OkHttpClient mOkHttpClient;

    /**
     * 创建OKHttp
     */
    public static OkHttpClient createOkHttpCleient() {
        if (mOkHttpClient == null) {
            //创建打印类
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    String text;
                    try {
                        text = URLDecoder.decode(message, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        text = e.getMessage();
                    }
                    LogUtil.d(TAG, text);
                }
            });
            //设置打印级别
            if (BuildConfig.DEBUG) {
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            //创建OkhttpClient
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();
                            builder.addHeader("contentType", "application/x-www-form-urlencoded; charset=UTF-8");
                            return chain.proceed(builder.build());
                        }
                    })
                    .addInterceptor(interceptor)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 创建ApiService
     */
    private static ApiService createFactory() {

        if (mOkHttpClient == null) {
            createOkHttpCleient();
        }

        //创建Rertofit - ApiService
        return new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(DecodeConverterFactory.create()) //添加自定义的加密解密规则构造器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiService.API_HOST)
                .build()
                .create(ApiService.class);
    }

    /**
     * 单例模式获取ApiService
     *
     * @return
     */
    public static ApiService getInstance() {
        if (apiService == null) {
            synchronized (ApiFactory.createFactory()) {
                if (apiService == null) {
                    apiService = ApiFactory.createFactory();
                }
            }
        }
        return apiService;
    }

}
