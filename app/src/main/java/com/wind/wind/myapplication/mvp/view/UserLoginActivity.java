package com.wind.wind.myapplication.mvp.view;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import android.app.Activity;

import com.wind.wind.myapplication.mvp.presenter.UserLoginPresenter;
import com.wind.wind.myapplication.mvp.bean.User;

/**
 * 类 名： UserLoginActivity<br>
 * 说 明：<br>
 * 创建日期：2018/8/21 0021<br>
 * 作 者：Devin Zhong<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：TODO<br>
 */
public class UserLoginActivity extends Activity implements IUserLoginView {
    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);
    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void clearUserName() {
    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toMainActivity(User user) {

    }

    @Override
    public void showFailedError() {

    }
}
