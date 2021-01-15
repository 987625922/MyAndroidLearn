package com.wyt.common.utils

import android.os.Environment

/**
 * @Author: LL
 * @Description:安卓各个路径的获取类
 * @Date:Create：in  2020/7/28 17:41
 */
object PathUtil {

    /**
     * 获取外部存储根目录
     * /storage/emulated/0
     */
    fun getStoragePath(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }
}