package com.learn.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyt.export_account.bean.UserInfo;
import com.wyt.export_account.router.AccountRouter;
import com.wyt.export_account.service.IAccountService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @Author: LL
 * @Description: 路由服务，其他module可以通过路由调用
 * @Date:Create：in 2021/1/19 15:04
 */
@Route(path = AccountRouter.PATH_SERVICE_ACCOUNT)
public class AccountService implements IAccountService {

    private UserInfo userInfo;

    @Override
    public boolean isLogin() {
        return userInfo != null;
    }

    @Override
    public String getAccountId() {
        return userInfo == null ? null : userInfo.getAccountId();
    }

    @Override
    public Fragment newUserFragment(Activity activity, int containerId, FragmentManager manager, Bundle bundle, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment userFragment = new UserFragment();
        transaction.add(containerId, userFragment, tag);
        transaction.commit();
        return userFragment;
    }

    @Override
    public void init(Context context) {
        //初始化工作，服务注入时会调用，可忽略
    }

    @Override
    public void setUserInfo(@Nullable UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @NotNull
    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }
}

