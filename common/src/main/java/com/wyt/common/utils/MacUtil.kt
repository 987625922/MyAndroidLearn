package com.wyt.common.utils

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.net.NetworkInterface
import java.util.*

/**
 * @Description:获取mac地址
 * https://www.jianshu.com/p/16d4ff4c4cbe
 */
object MacUtil {
    /**
     * 获取失败默认返回值
     */
    private const val ERROR_MAC_STR = "02:00:00:00:00:00"

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    fun getMacAddress(context: Context?): String? {
        var mac: String? = ERROR_MAC_STR
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = macFromFile
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = macFromHardware
        }
        return mac
    }

    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param context
     * @return
     */
    private fun getMacDefault(context: Context?): String? {
        var mac = ERROR_MAC_STR
        if (context == null) {
            return mac
        }
        val wifi = context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
            ?: return mac
        var info: WifiInfo? = null
        try {
            info = wifi.connectionInfo
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (info == null) {
            return null
        }
        mac = info.macAddress
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH)
        }
        return mac
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     *
     * @return
     */
    private val macFromFile: String
        private get() {
            var wifiAddress = ERROR_MAC_STR
            try {
                wifiAddress =
                    BufferedReader(FileReader(File("/sys/class/net/wlan0/address")))
                        .readLine()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return wifiAddress
        }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     *
     * @return
     */
    private val macFromHardware: String
        private get() {
            try {
                val all: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (nif in all) {
                    if (!nif.name.equals("wlan0", ignoreCase = true)) continue
                    val macBytes = nif.hardwareAddress ?: return ""
                    val res1 = StringBuilder()
                    for (b in macBytes) {
                        res1.append(String.format("%02X:", b))
                    }
                    if (res1.length > 0) {
                        res1.deleteCharAt(res1.length - 1)
                    }
                    return res1.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ERROR_MAC_STR
        }
}