package com.wyt.hs.zxxtb.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.wyt.hs.zxxtb.BuildConfig;
import com.wyt.hs.zxxtb.util.GsonUtil;
import com.wyt.hs.zxxtb.util.LogUtils;


import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;


/**
 * 加密DecodeRequestBodyConverter
 */
public final class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");

    DecodeRequestBodyConverter(Gson gson,TypeAdapter<T> adapter){

    }
    @Override
    public RequestBody convert(final T value) throws IOException {
        //获得value的json
        String jsonStirng = GsonUtil.GsonString(value);
        LogUtils.d("ApiFactory","加密前的请求参数 = " + jsonStirng);
        //参数转化成Map
        Map<String,String> map = GsonUtil.GsonToMaps(jsonStirng);
        //获得加密字符串
        String json = Base64Utils.getEncodedPostString(map, BuildConfig.REQUEST_KEY);
        return RequestBody.create(MEDIA_TYPE,json);
    }
}
