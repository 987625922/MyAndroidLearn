package com.wind.wind.androidlearn.mvp.view;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import android.app.Activity;

import com.wind.wind.androidlearn.mvp.presenter.UserLoginPresenter;
import com.wind.wind.androidlearn.mvp.bean.User;


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
