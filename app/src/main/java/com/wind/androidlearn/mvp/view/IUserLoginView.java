package com.wind.androidlearn.mvp.view;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import com.wind.androidlearn.mvp.bean.User;


public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
