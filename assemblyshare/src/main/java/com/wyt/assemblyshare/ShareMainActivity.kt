package com.wyt.assemblyshare

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wyt.export_account.AccountServiceUtil
import com.wyt.export_account.router.AccountRouter


@Route(path = "/share/main")
class ShareMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        if (intent != null) {
            val content = intent.getStringExtra("share_content")
            if (!TextUtils.isEmpty(content)) {
                (findViewById<View>(R.id.share_content) as TextView).text = content
            }
        }
        share()
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()

        //使用ARouter获取Fragment实例 并添加
        val userFragment: Fragment = ARouter.getInstance().build(AccountRouter.PATH_FRAGMENT_ACCOUNT).navigation() as Fragment
        transaction.add(R.id.rlLogin, userFragment, "tag")
        transaction.commit()
    }

    private fun share() {
        if (AccountServiceUtil.getService().isLogin()) {
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "分享失败：用户未登录", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareLogin(view: View?) {
        ARouter.getInstance().build("/account/login").navigation()
    }
}