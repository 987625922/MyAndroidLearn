package com.wind.wind.myapplication.mvp.biz;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import com.wind.wind.myapplication.mvp.bean.User;


public interface OnLoginListener {

    void loginSuccess(User user);

    void loginFailed();
}
