package com.wyt.common.network.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * 中山后台的规则 - 加密与解密的ConverterFactory
 */
class DecodeXuetangConverterFactory private constructor(gson: Gson) : Converter.Factory() {
    private val gson: Gson = gson

    companion object {
        fun create(gson: Gson? = Gson()): DecodeXuetangConverterFactory {
            return DecodeXuetangConverterFactory(gson!!)
        }
    }

    /**
     * request加密
     */
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return DecodeXuetangRequestBodyConverter<Any>()
    }

    /**
     * response解密
     */
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return DecodeXuetangResponseBodyConverter(gson, adapter)
    }
}