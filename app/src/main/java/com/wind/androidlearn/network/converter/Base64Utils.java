package com.wind.androidlearn.network.converter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.JsonObject;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * base64 加密与解密
 */
public final class Base64Utils {

    /**
     * 获取用于请求服务器的加密字符串
     *
     * @param args    请求参数
     * @param signKey 请求签名的密钥
     * @return 加密后的字符串
     */
    @NonNull
    public static String getEncodedPostString(final Map<String, String> args, final String signKey) {

        final JsonObject resultObject = new JsonObject();
        final String data;
        final String sign;

        //将所有参数进行升序排序
        final Map<String, String> temp = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        temp.putAll(args);

        //得到用于生成签名的字符串
        final StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : temp.entrySet()) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(entry.getValue());
            } else {
                stringBuilder.append(",").append(entry.getValue());
            }
        }
        stringBuilder.append(signKey);

        //生成Json数据字符串
        final JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, String> entry : temp.entrySet()) {
            jsonObject.addProperty(entry.getKey(), entry.getValue());
        }

        //加密处理
        data = BASE64_ENCODE(jsonObject.toString());
        sign = MD5(stringBuilder.toString());

        resultObject.addProperty("data", data);
        resultObject.addProperty("sign", sign);

        return resultObject.toString();
    }

    /**
     * 进行MD5加密
     *
     * @param text 要加密的字符串
     * @return 加密后的字符串，处理失败返回原本文字
     */
    @NonNull
    private static String BASE64_ENCODE(@NonNull String text) {
        return new String(Base64.encode(text.getBytes(), Base64.DEFAULT));
    }

    /**
     * 进行MD5加密
     * @param text 要加密的字符串
     * @return 加密后的字符串，处理失败返回原本文字
     */
    @NonNull
    public static String MD5(@NonNull String text) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = text.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return text;
        }
    }

    @NonNull
    public static String decodeBASE64(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        return new String(Base64.decode(text.getBytes(), Base64.DEFAULT));
    }

}
