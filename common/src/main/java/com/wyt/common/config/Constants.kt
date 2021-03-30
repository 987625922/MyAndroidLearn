package com.wyt.common.config

import okhttp3.MediaType

/**
 * @Author: LL
 * @Description: 常量配置类
 * @Date:Create：in  2020/7/28 10:03
 */
object Constants {
    /**
     * 请求链接
     */
    object Http {
        /**
         * 机器人后台请求链接
         */
        const val ROBOT_BASE_URL = ""

        /**
         * 中山后台请求链接
         */
        const val XUETANG_BASE_URL = ""

        /**
         * 机器人后台网络请求的秘钥
         */
        const val ROBOT_REQUEST_KEY = ""

        /**
         * 表单提交的请求头
         */
        val MEDIA_TYPE =
            MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8")
    }
}