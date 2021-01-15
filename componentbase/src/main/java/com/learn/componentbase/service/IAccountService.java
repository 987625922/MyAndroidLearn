package com.learn.componentbase.service;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/15 14:12
 */
public interface IAccountService {
    /**
     * 是否已经登录
     *
     * @return
     */
    boolean isLogin();

    /**
     * 获取登录用户的 AccountId
     *
     * @return
     */
    String getAccountId();


    /**
     * 创建 UserFragment
     * @param activity
     * @param containerId
     * @param manager
     * @param bundle
     * @param tag
     * @return
     */
    Fragment newUserFragment(Activity activity, int containerId, FragmentManager manager, Bundle bundle, String tag);
}
