package com.learn.componentbase.empty_service;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.learn.componentbase.service.IAccountService;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/15 14:14
 */
public class EmptyAccountService implements IAccountService {
    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public String getAccountId() {
        return null;
    }

    @Override
    public Fragment newUserFragment(Activity activity, int containerId, FragmentManager manager, Bundle bundle, String tag) {
        return null;
    }
}
