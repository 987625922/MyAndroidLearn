package com.wyt.common.network.converter

import com.wyt.common.config.Constants
import com.wyt.common.config.Constants.Http.MEDIA_TYPE
import com.wyt.common.utils.GsonUtil
import com.wyt.common.utils.Log
import com.wyt.common.utils.RobotRequestUtil
import okhttp3.RequestBody
import retrofit2.Converter

/**
 * 机器人后台的加密DecodeRequestBodyConverter
 */
class DecodeRequestBodyConverter<T> : Converter<T, RequestBody> {
    override fun convert(value: T): RequestBody {
        //获得value的json
        val jsonStirng = GsonUtil.gsonString(value)
        Log.d("ApiFactory加密前的请求参数 = $jsonStirng")
        //参数转化成Map
        val map =
            GsonUtil.gson2Map(jsonStirng)
        //获得加密字符串
        val json = RobotRequestUtil.getEncodedPostString(
            map,
            Constants.Http.ROBOT_REQUEST_KEY
        )
        Log.d("ApiFactory加密后的请求参数 = $json")
        return RequestBody.create(
            MEDIA_TYPE,
            json
        )
    }
}