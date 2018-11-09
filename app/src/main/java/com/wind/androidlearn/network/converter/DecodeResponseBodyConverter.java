package com.wind.androidlearn.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.wind.androidlearn.bassis.Utils.LogUtil;
import com.wind.androidlearn.network.base.BaseEntity;
import com.wind.androidlearn.network.excption.ResponseErrorException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * 解码GsonCoverter
 * 拦截 返回结果成功，但code不为200的情况
 *
 * @param <T>
 */
public final class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;

    DecodeResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        LogUtil.d("ApiServiceFactory", "响应参数：" + response);
        try {
            //解密字符串
            response = Base64Utils.decodeBASE64(new JSONObject(response).getString("data"));
            LogUtil.d("ApiServiceFactory", "解密结果：" + response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        if (response.contains("code") && response.contains("msg") && response.contains("data")) {
            BaseEntity status = gson.fromJson(response, BaseEntity.class);
            if (!status.isSuccess()) {
                value.close();
                if (status != null) {
                    String msg;
                    if (status.msg == null || status.msg.isEmpty()) {
                        msg = "错误码:" + status.code;
                    } else {
                        msg = status.msg;
                    }
                    throw new ResponseErrorException("返回码不为200", msg, status.code);
                }
            }
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}