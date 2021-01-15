package com.wyt.common.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.wyt.common.R

/**
 * Toast工具类
 */
object ToastUtil {
    fun show(context: Context?, content: String?) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showLong(context: Context?, content: String?) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show()
    }

    /**
     * 显示成功的提示
     *
     * @param context
     * @param content
     */
    fun showSuccessToast(context: Context, content: String) {
        showStatusToast(context, true, content)
    }

    /**
     * 显示成功的提示
     *
     * @param context
     * @param content
     */
    fun showFailToast(context: Context, content: String) {
        showStatusToast(context, false, content)
    }

    /**
     * 显示一个自定义的状态Toast
     *
     * @param context
     * @param content 内容
     */
    private fun showStatusToast(
        context: Context,
        success: Boolean,
        content: String
    ) {
        if (TextUtils.isEmpty(content)) {
            return
        }
        val toast = Toast(context)
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.common_toast_status_tip, null)
        toast.view = view
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        tvStatus.text = content
        val ivStatus =
            view.findViewById<ImageView>(R.id.iv_status)
        ivStatus.isSelected = success
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}