package com.wyt.common.utils

import com.wyt.common.BuildConfig

/**
 * @Description:Log 工具类
 */
object Log {
    private val isDebug = BuildConfig.DEBUG
    private const val packageName = BuildConfig.APPLICATION_ID

    @JvmStatic
    fun d(message: Any) {
        if (isDebug) {
            println("$packageName ==> $message")
        }
    }

    @JvmStatic
    fun i(message: Any) {
        println("日志输出 $packageName ==> $message")
    }
}