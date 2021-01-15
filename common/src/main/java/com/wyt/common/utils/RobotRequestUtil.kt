package com.wyt.common.utils

import com.google.gson.JsonObject
import java.util.*
import kotlin.Comparator

/**
 * @Author: LL
 * @Description:机器人请求的工具类
 * @Date:Create：in  2020/7/28 13:45
 */
object RobotRequestUtil {

    /**
     * 获取用于请求服务器的加密字符串 - 机器人后台
     *
     * @param args    请求参数
     * @param signKey 请求签名的密钥
     * @return 加密后的字符串
     */
    @JvmStatic
    fun getEncodedPostString(args: Map<String, String>, signKey: String): String {
        val resultObject = JsonObject()
        val data: String
        val sign: String

        //将所有参数进行升序排序
        val temp: MutableMap<String, String> =
            TreeMap(Comparator { o1, o2 -> o1.compareTo(o2) })
        temp.putAll(args)

        //得到用于生成签名的字符串
        val stringBuilder = StringBuilder()
        for ((key, value) in temp) {
            if (stringBuilder.isEmpty()) {
                stringBuilder.append(value)
            } else {
                stringBuilder.append(",").append(value)
            }
        }
        stringBuilder.append(signKey)

        //生成Json数据字符串
        val jsonObject = JsonObject()
        for ((key, value) in temp) {
            jsonObject.addProperty(key, value)
        }

        //加密处理
        data = Base64Util.encodeBASE64(jsonObject.toString())!!.replace("[\r\n]".toRegex(), "")
        sign = Md5Util.decodeMd5(stringBuilder.toString()).replace("[\r\n]".toRegex(), "")
        resultObject.addProperty("data", data)
        resultObject.addProperty("sign", sign)
        return resultObject.toString()
    }
}