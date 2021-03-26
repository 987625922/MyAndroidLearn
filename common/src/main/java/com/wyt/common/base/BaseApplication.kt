package com.wyt.common.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.wyt.common.BuildConfig
import kotlin.properties.Delegates

/**
 * @Author: LL
 * @Description: app的基类
 * @Date:Create：in 2020/7/2 16:34
 */
abstract class BaseApplication : Application() {

    companion object {

        var instance: BaseApplication by Delegates.notNull()

        fun instance() =
                instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 初始化 ARouter

        // 初始化 ARouter
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }


    private fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}