package com.wyt.common.utils

import android.util.Base64
import androidx.annotation.NonNull

/**
 * @Description:base64 加密与解密
 * @author LL
 */
object Base64Util {

    /**
     * 进行base64加密
     */
    fun encodeBASE64(@NonNull params: String): String {
        return String(Base64.encode(params.toByteArray(), Base64.URL_SAFE))
    }

    /**
     * base64解密
     */
    fun decodeBASE64(@NonNull params: String): String {
        return String(Base64.decode(params.toByteArray(), Base64.URL_SAFE))
    }
}