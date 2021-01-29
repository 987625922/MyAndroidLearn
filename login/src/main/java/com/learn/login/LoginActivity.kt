package com.learn.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wyt.export_account.AccountServiceUtil
import com.wyt.export_account.bean.UserInfo

/**
 * @Author: LL
 * @Description:组件化登录界面
 * @Date:Create：in 2021/1/19 15:20
 */
@Route(path = "/account/login")
class LoginActivity : AppCompatActivity() {
    private var tvState: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        updateLoginState()
    }

    private fun initView() {
        tvState = findViewById(R.id.tv_login_state)
    }

    fun login(view: View?) {
        AccountServiceUtil.getService().setUserInfo(UserInfo("10086", "Admin"))
        updateLoginState()
    }

    private fun updateLoginState() {
        tvState!!.text = "这里是登录界面：" + if (AccountServiceUtil.getService().getUserInfo() == null) "未登录" else AccountServiceUtil.getService().getUserInfo().userName
    }

    fun exit(view: View?) {
        AccountServiceUtil.getService().setUserInfo(null)
        updateLoginState()
    }

    fun loginShare(view: View?) {
        ARouter.getInstance().build("/share/main")
                .withString("share_content", "分享数据到微博").navigation()
    }
}