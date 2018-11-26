package com.wind.androidlearn.network;

import com.wind.androidlearn.BaseActivity;
import com.wind.androidlearn.mvp.bean.User;
import com.wind.androidlearn.network.params.Params;
import com.wind.androidlearn.network.params.ParamsBuilder;
import com.wind.androidlearn.network.schedulers.RxSchedulers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NetWorkActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void findView() {
        ApiFactory.getInstance().getAliAppOrder(new ParamsBuilder() {
            @Override
            protected Params convert(Params params) {
                params.uid = "";
                return params;
            }
        }.create()).compose(RxSchedulers.<User>compose())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User payOrderInfo) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }
}
