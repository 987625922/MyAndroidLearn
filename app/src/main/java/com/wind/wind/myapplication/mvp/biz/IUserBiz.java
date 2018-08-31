package com.wind.wind.myapplication.mvp.biz;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}
