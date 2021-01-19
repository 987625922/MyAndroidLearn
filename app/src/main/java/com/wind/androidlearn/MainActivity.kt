package com.wind.androidlearn

import com.alibaba.android.arouter.launcher.ARouter
import com.wyt.common.base.BaseActivity
import com.wyt.zdf.myapplication.R
import kotlinx.android.synthetic.main.app_acitivity_main.*

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2021/1/19 14:29
 */
class MainActivity:BaseActivity() {
    override fun start() {
    }

    override fun initData() {
    }

    override fun getLayout() = R.layout.app_acitivity_main

    override fun initView() {
        btn1.setOnClickListener {
            ARouter.getInstance().build("/learn/main").navigation()
        }
        btn2.setOnClickListener {
            ARouter.getInstance().build("/account/login").navigation()
        }
    }

}