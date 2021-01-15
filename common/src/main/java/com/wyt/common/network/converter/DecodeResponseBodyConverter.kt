package com.wyt.common.network.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.wyt.common.network.base.BaseEntity
import com.wyt.common.network.excption.ApiException
import com.wyt.common.utils.Base64Util
import com.wyt.common.utils.Log
import okhttp3.ResponseBody
import okhttp3.internal.Util
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.*
import kotlin.text.Charsets.UTF_8

/**
 * 机器人后台的解码GsonCoverter
 * 拦截 返回结果成功，但code不为200的情况
 * @param <T>
 */
class DecodeResponseBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T?> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        var response = value.string()
        Log.d("ApiServiceFactory响应参数：$response")
        try {
            //解密字符串
            response = Base64Util.decodeBASE64(
                JSONObject(response).getString("data")
            )
            Log.d("ApiServiceFactory解密结果：$response")
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
        if (response.contains("code") && response.contains("msg")) {
            val baseEntity =
                gson.fromJson(response, BaseEntity::class.java)
            if (baseEntity.code != 200) {
                value.close()
                baseEntity.let {
                    throw ApiException(
                        if (baseEntity.msg.isEmpty()) {
                            "错误码:" + baseEntity.code
                        } else {
                            baseEntity.msg
                        },
                        baseEntity.code
                    )
                }
            }
        }
        val contentType = value.contentType()
        val charset =
            if (contentType != null) contentType.charset(UTF_8) else UTF_8
        val inputStream: InputStream = ByteArrayInputStream(response.toByteArray())
        val reader: Reader = InputStreamReader(inputStream, charset)
        val jsonReader = gson.newJsonReader(reader)
        return value.use {
            adapter.read(jsonReader)
        }
    }
}