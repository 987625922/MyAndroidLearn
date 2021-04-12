package com.wyt.common.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * @Description:文件处理工具类
 * @author: LL
 */
object FileUtil {
    /**
     * 字符流读取文件
     *
     * @param filePath 文件地址
     * @return 字符串
     */
    fun read(filePath: String?): String {
        val stringBuilder = StringBuilder()
        try {
            FileInputStream(filePath).use { inputStream ->
                val temp = ByteArray(1024)
                var len = 0
                while (inputStream.read(temp).also { len = it } > 0) {
                    stringBuilder.append(String(temp, 0, len))
                }
                return stringBuilder.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 写文件
     *
     * @param filePath 文件地址
     * @param content  文件内容
     * @Return 写文件是否成功
     */
    fun write(filePath: String?, content: String): Boolean {
        try {
            FileOutputStream(filePath).use { output ->
                output.write(content.toByteArray())
                return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun createNewFile(path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
    }

}