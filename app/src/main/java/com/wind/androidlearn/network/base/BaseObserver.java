package com.wind.androidlearn.network.base;

import com.google.gson.JsonParseException;
import com.wind.androidlearn.network.excption.ResponseErrorException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 基类观察者
 */
public abstract class BaseObserver<T extends BaseEntity> implements Observer<T> {

    private static final int ERROR_CODE = -1;

    @Override
    public void onSubscribe(Disposable d) {


    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            onFail(new ResponseErrorException("HTTP错误", e.getMessage(), ERROR_CODE));
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onFail(new ResponseErrorException("网络连接错误", e.getMessage(), ERROR_CODE));
        } else if (e instanceof ConnectTimeoutException) {
            onFail(new ResponseErrorException("网络连接超时", e.getMessage(), ERROR_CODE));
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onFail(new ResponseErrorException("数据解析错误", e.getMessage(), ERROR_CODE));
        } else if (e instanceof ResponseErrorException) {
            onFail(new ResponseErrorException(
                    ((ResponseErrorException) e).name,
                    ((ResponseErrorException) e).msg,
                    ((ResponseErrorException) e).errorCode)
            );
        } else {
            onFail(new ResponseErrorException("未知错误", e.getMessage(), ERROR_CODE));
        }
        onComplete();
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     */
    protected abstract void onSuccess(T entity);

    /**
     * 请求失败
     */
    protected void onFail(ResponseErrorException e) {
    }

    ;

}
