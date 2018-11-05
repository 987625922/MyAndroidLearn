package com.wyt.hs.zxxtb.network;

/**
 * 返回结果回调
 * @param <T> 数据
 */
public interface CallBack<T> {
    void onSuccess(T t);
    void onFail(Throwable e);
}
