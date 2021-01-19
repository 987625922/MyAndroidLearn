package com.wyt.common.base

import android.app.Application
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
    }
    /**
     * Application 初始化
     */
    public open abstract fun initModuleApp(application: Application)
    /**
     * 所有 Application 初始化后的自定义操作
     */
    public open abstract fun initModuleData(application: Application)

    private fun initMainThread() {
//        if (!BuildConfig.DEBUG) {
        //buggly
//            CrashReport.initCrashReport(applicationContext, "966e182d7b", false)
//        }
        //头条适配
//        DensityUtil.setDensity(this)
    }
}