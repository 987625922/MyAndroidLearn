package com.wind.wind.myapplication.mvp.presenter;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import android.os.Handler;

import com.wind.wind.myapplication.mvp.bean.User;
import com.wind.wind.myapplication.mvp.biz.IUserBiz;
import com.wind.wind.myapplication.mvp.biz.OnLoginListener;
import com.wind.wind.myapplication.mvp.biz.UserBiz;
import com.wind.wind.myapplication.mvp.view.IUserLoginView;


public class UserLoginPresenter {

    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView)
    {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }

    public void login()
    {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener()
        {
            @Override
            public void loginSuccess(final User user)
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });

            }

            @Override
            public void loginFailed()
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });

            }
        });
    }

    public void clear()
    {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }


}
