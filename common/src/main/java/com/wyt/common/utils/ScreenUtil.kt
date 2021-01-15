package com.wyt.common.utils

import android.content.Context

/**
 * @Author: LL
 * @Description:屏幕分辨率相关
 * @Date:Create：in  2020/10/13 14:05
 */
object ScreenUtil {

    /**
     * dp转px
     * 16dp - 48px
     * 17dp - 51px */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}