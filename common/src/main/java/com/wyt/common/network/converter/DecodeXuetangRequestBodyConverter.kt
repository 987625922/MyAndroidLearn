package com.wyt.common.network.converter

import android.text.TextUtils
import com.google.gson.JsonObject
import com.wyt.common.config.Constants.Http.MEDIA_TYPE
import com.wyt.common.utils.Base64Util
import com.wyt.common.utils.EncriptionUtil
import com.wyt.common.utils.GsonUtil
import okhttp3.RequestBody
import retrofit2.Converter
import java.io.IOException
import java.util.*

/**
 * 中山后台的加密DecodeRequestBodyConverter
 */
class DecodeXuetangRequestBodyConverter<T> internal constructor() :
    Converter<T, RequestBody> {
    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        //获得value的json
        var jsonStirng = GsonUtil.gsonString(value)
        //请求的参数
        val resultObject = JsonObject()
        resultObject.addProperty("data", jsonStirng)
        //参数转化成Map
        val map = GsonUtil.gson2Map(jsonStirng)
        //将所有参数进行升序排序
        val temp: MutableMap<String, String> =
            TreeMap(Comparator { o1, o2 -> o1.compareTo(o2) })
        temp.putAll(map!!)
        jsonStirng = ""
        //对map的value进行字符串格式化
        for (key in temp.keys) {
            val mapValue = temp[key]
            jsonStirng += if (TextUtils.isEmpty(jsonStirng)) {
                mapValue
            } else {
                ",$mapValue"
            }
        }
        //合并字符串
        jsonStirng = "20349323327614:" + Base64Util.decodeBASE64(
            EncriptionUtil.hamcsha1(
                jsonStirng.toByteArray(),
                "l6_pi82EpcqDiSZvEtuHXcf5wGHp1Q".toByteArray()
            ).toLowerCase()
        )
        //把合并的字符串作为token
        resultObject.addProperty("token", jsonStirng.replace("\n".toRegex(), ""))
        val body =
            resultObject.toString().replace("\\\\".toRegex(), "").replace("\"{", "{").replace(
                "}\"", "}"
            )
        return RequestBody.create(MEDIA_TYPE, body)
    }
}