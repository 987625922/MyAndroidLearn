package com.learn.learn.bassis.mvp;

public class UserLoginPresenter {
    private IUserLoginView iUserLoginView;

    public UserLoginPresenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
    }

    public void login(){
        iUserLoginView.showLoading();
    }
}
