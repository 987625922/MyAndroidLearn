package com.learn.learn.bassis.mvp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class UserLoginActivity extends Activity implements IUserLoginView {

    private UserLoginPresenter userLoginPresenter
            = new UserLoginPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoginPresenter.login();
    }

    @Override
    public void showLoading() {

    }
}
