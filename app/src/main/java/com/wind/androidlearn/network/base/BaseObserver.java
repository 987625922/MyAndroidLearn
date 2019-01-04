package com.wyt.hs.zxxtb.network.base;

import com.google.gson.JsonParseException;
import com.wyt.hs.zxxtb.activity.BaseActivity;
import com.wyt.hs.zxxtb.fragment.BaseFragment;
import com.wyt.hs.zxxtb.network.excption.ResponseErrorException;
import com.wyt.hs.zxxtb.util.NetworkUtil;
import com.wyt.hs.zxxtb.widget.NetworkDialog;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 基类观察者
 */
public abstract class BaseObserver<T extends BaseEntity> implements Observer<T>{

    private static final int ERROR_CODE = -1;

    private WeakReference<BaseActivity> mActivity;

    public BaseObserver(BaseActivity activity){
        mActivity = new WeakReference<>(activity);
    }

    public BaseObserver(BaseFragment fragment){
        mActivity = new WeakReference<>((BaseActivity)fragment.getActivity());
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mActivity!=null && mActivity.get()!=null){
            if (!NetworkUtil.isNetworkAvailable(mActivity.get())){
                onError(new ResponseErrorException("Network non","网络不可用，请检查",ERROR_CODE));
                d.dispose();
            }
            mActivity.get().addDisponse(d);
        }

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException){
            onFail(new ResponseErrorException("HTTP错误",e.getMessage(),ERROR_CODE));
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onFail(new ResponseErrorException("网络连接错误",e.getMessage(),ERROR_CODE));
        } else if (e instanceof ConnectTimeoutException) {
            onFail(new ResponseErrorException("网络连接超时",e.getMessage(),ERROR_CODE));
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onFail(new ResponseErrorException("数据解析错误",e.getMessage(),ERROR_CODE));
        } else if (e instanceof ResponseErrorException){
            onFail(new ResponseErrorException(
                    ((ResponseErrorException) e).name,
                    ((ResponseErrorException) e).msg,
                    ((ResponseErrorException) e).errorCode)
            );
        } else {
            onFail(new ResponseErrorException("未知错误",e.getMessage(),ERROR_CODE));
        }
        onComplete();
    }

    @Override
    public void onComplete() {}

    /**
     * 请求成功
     */
    protected abstract void onSuccess(T entity);

    /**
     * 请求失败
     */
    protected void onFail(ResponseErrorException e){};

}
