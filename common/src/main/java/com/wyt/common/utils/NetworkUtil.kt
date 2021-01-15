package com.wyt.common.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @Description:网络工具类
 */
object NetworkUtil {
    /**
     * 获取失败默认返回值
     */
    private const val ERROR_IP_STR = "127.0.0.1"

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context
            .applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            ?: return false
        val networkinfo = manager.activeNetworkInfo
        return networkinfo != null && networkinfo.isAvailable
    }
}