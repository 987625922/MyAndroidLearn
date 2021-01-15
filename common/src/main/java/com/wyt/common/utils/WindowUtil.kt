package com.wyt.common.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * 屏幕工具类
 */
object WindowUtil {
    /**
     * 判断是否是透明背景的Activity
     *
     * @param context
     * @return
     */
    fun isTranslucentOrFloating(context: Context): Boolean {
        var isTranslucentOrFloating = false
        try {
            val styleableRes =
                Class.forName("com.android.internal.R\$styleable")
                    .getField("Window")[null] as IntArray
            val ta = context.obtainStyledAttributes(styleableRes)
            val m = ActivityInfo::class.java.getMethod(
                "isTranslucentOrFloating",
                TypedArray::class.java
            )
            m.isAccessible = true
            isTranslucentOrFloating = m.invoke(null, ta) as Boolean
            m.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isTranslucentOrFloating
    }

    /**
     * 修复8.0以上透明背景Activity指定方向时候闪退的bug
     *
     * @param activity
     */
    fun fixOrientation(activity: Activity?) {
        try {
            val field =
                Activity::class.java.getDeclaredField("mActivityInfo")
            field.isAccessible = true
            val o = field[activity] as ActivityInfo
            o.screenOrientation = -1
            field.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 全屏 - 隐藏状态栏和虚拟按键
     *
     * @param window
     */
    @JvmStatic
    fun setHideVirtualKey(window: Window) {
        //保持布局状态
        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or  //布局位于状态栏下方
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or  //全屏
                View.SYSTEM_UI_FLAG_FULLSCREEN or  //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        uiOptions = if (Build.VERSION.SDK_INT >= 19) {
            uiOptions or 0x00001000
        } else {
            uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
        }
        window.decorView.systemUiVisibility = uiOptions
    }

    /**
     * 改变屏幕亮度
     */
    /**
     * 设置页面的透明度	 * @param bgAlpha 1表示不透明
     */
    fun setBackgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha
        if (bgAlpha == 1f) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        activity.window.attributes = lp
    }

    /**
     * 打印当前设备分辨率
     */
    fun printWindowsSize(context: Context) {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        Log.i("当前屏幕分辨率：宽: $width  高: $height")
    }
    private var density = -1f
    private var widthPixels = -1
    private var heightPixels = -1

    private fun DensityUtil() {}

    fun getScreenWidth(context: Context): Int {
        if (widthPixels <= 0) {
            widthPixels = context.resources.displayMetrics.widthPixels
        }
        return widthPixels
    }


    fun getScreenHeight(context: Context): Int {
        if (heightPixels <= 0) {
            heightPixels = context.resources.displayMetrics.heightPixels
        }
        return heightPixels
    }
}