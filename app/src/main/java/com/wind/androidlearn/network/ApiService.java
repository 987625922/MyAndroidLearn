package com.wind.androidlearn.network;


import com.wind.androidlearn.mvp.bean.User;
import com.wind.androidlearn.network.params.Params;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    //测试地址
    String API_HOST = "http://12.12.12.38/";

    /**
     */
    @POST("api/order/app")
    Observable<User> getAliAppOrder(@Body Params params);
}
