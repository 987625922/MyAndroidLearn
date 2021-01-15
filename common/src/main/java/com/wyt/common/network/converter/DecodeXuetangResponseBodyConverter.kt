package com.wyt.common.network.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.wyt.common.utils.Log
import okhttp3.ResponseBody
import okhttp3.internal.Util
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.*
import java.nio.charset.StandardCharsets.UTF_8

/**
 * 中山后台的解码GsonCoverter
 * 拦截 返回结果成功，但code不为200的情况
 *
 */
class DecodeXuetangResponseBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T?> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        var response = value.string()
        Log.d("ApiServiceFactory响应参数：$response")
        try {
            response = JSONObject(response).getString("data")
            response.replace("\\r\\n", "\r\n").replace("%", "")
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
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