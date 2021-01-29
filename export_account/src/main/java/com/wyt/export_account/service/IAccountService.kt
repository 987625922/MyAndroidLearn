package com.wyt.export_account.service

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.facade.template.IProvider
import com.wyt.export_account.bean.UserInfo

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2021/1/29 15:14
 */
interface IAccountService : IProvider {
    /**
     * 是否已经登录
     *
     * @return
     */
    fun isLogin(): Boolean

    /**
     * 获取登录用户的 AccountId
     *
     * @return
     */
    fun getAccountId(): String?

    /**
     * 设置userinfo
     */
    fun setUserInfo(userInfo: UserInfo?)

    /**
     * 获取userinfo
     */
    fun getUserInfo(): UserInfo


    /**
     * 创建 UserFragment
     *
     * @param activity
     * @param containerId
     * @param manager
     * @param bundle
     * @param tag
     * @return
     */
    fun newUserFragment(activity: Activity?, containerId: Int, manager: FragmentManager?, bundle: Bundle?, tag: String?): Fragment?
}