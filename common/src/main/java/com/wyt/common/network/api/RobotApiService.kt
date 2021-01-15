package com.wyt.common.network.api

import com.wyt.common.config.Constants
import com.wyt.common.network.converter.DecodeConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author: LL
 * @Description: 机器人后台api接口
 * @Date:Create：in  2020/7/24 18:16
 */
interface RobotApiService {

    /**
     * 请求apk信息
     */
    @POST("php/watch/versions.api/get_infos")
    suspend fun getApkInfo(
        @Body map: Map<String, String>
    ): Datast


    @POST("php/watch/versions.api/get_infos")
    suspend fun getApkInfo1(
        @Body map: Map<String, String>
    ): ApkInfo


    companion object {
        val instance: RobotApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            create()
        }

        private fun create(): RobotApiService {
            val logger =
                HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.NONE }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.Http.ROBOT_BASE_URL)
                .client(client)
                .addConverterFactory(DecodeConverterFactory.create())
                .build()
                .create(RobotApiService::class.java)
        }
    }
}