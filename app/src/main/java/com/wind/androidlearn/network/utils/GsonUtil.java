package com.wind.wind.androidlearn.network.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {

    }

    /**
     * 转成json
     *
     * @param object 对象
     * @return 字符串
     */
    public static String GsonString(Object object) {
        String gsonString = "";
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString 字符串
     * @param cls        类
     * @return T
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json json
     * @param cls  类
     * @param <T>  T
     * @return T列表
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString 字符串
     * @return 列表
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString 字符串
     * @return map
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 将Object对象转换成json，并排除int=0的属性
     *
     * @param o
     * @return
     */
    public static String toFilterString(final Object o) {

        final Class beanClass = o.getClass();
        return new GsonBuilder()
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        try {
                            Field field = beanClass.getField(f.getName());
                            field.setAccessible(true);
                            Object val = field.get(o); //设置此属性的值
                            String type = field.getType().toString(); //属性类型
                            if ("int".endsWith(type) && ((Integer) val) == 0) {
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create()
                .toJson(o);
    }
}