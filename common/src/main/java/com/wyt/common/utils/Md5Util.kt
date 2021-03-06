package com.wyt.common.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @Author: LL
 * @Description:Md5加密工具类
 * @Date:Create：in  2020/7/28 13:46
 */
object Md5Util {

    /**
     * md5加密
     */
    fun decodeMd5(param: String): String {
        try {
            val instance: MessageDigest = MessageDigest.getInstance("MD5")//获取md5加密对象
            val digest: ByteArray = instance.digest(param.toByteArray())//对字符串加密，返回字节数组
            var sb: StringBuffer = StringBuffer()
            for (b in digest) {
                var i: Int = b.toInt() and 0xff//获取低八位有效值
                var hexString = Integer.toHexString(i)//将整数转化为16进制
                if (hexString.length < 2) {
                    hexString = "0$hexString"//如果是一位的话，补0
                }
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

}