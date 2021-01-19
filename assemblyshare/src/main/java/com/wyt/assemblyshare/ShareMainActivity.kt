package com.wyt.assemblyshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.learn.componentbase.ServiceFactory

@Route(path = "/sharee/main")
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
    }

    private fun share() {
        if (ServiceFactory.getInstance().accountService.isLogin) {
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "分享失败：用户未登录", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareLogin(view: View?) {
        ARouter.getInstance().build("/account/login").navigation()
    }
}