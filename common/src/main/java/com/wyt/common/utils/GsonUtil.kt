package com.wyt.common.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @Description:gson工具类
 * @author:LL
 */
object GsonUtil {
    private val gson: Gson by lazy {
        GsonBuilder().create()
    }

    /**
     * 转成json
     *
     * @param object 对象
     * @return 字符串
     */
    @JvmStatic
    fun gsonString(bean: Any?): String {
        return gson.toJson(bean)
    }

    /**
     * 转成bean
     * @param gsonString 字符串
     * @param cls        类
     * @return T
     */
    @JvmStatic
    fun <T> gsonToBean(gsonString: String, cls: Class<T>): T {
        return gson.fromJson(gsonString, cls)
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json json
     * @param cls  类
     * @param <T>  T
     * @return T列表
    </T> */
    fun <T> jsonToList(json: String, cls: Class<T>): List<T> {
        val list: MutableList<T> = ArrayList()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString 字符串
     * @return 列表
     */
    fun <T> gsonToListMaps(gsonString: String): List<Map<String, T>> {
        return gson.fromJson(
            gsonString,
            object :
                TypeToken<List<Map<String, T>>>() {}.type
        )
    }

    /**
     * 转成map的
     *
     * @param gsonString 字符串
     * @return map
     */
    fun <T> gsonToMaps(gsonString: String): Map<String, T> {
        return gson.fromJson(
            gsonString,
            object : TypeToken<Map<String, T>>() {}.type
        )
    }

    /**
     * jdon转成treemap的
     *
     * @param gsonString 字符串
     * @return map
     */
    @JvmStatic
    fun <T> gsonToTreeMaps(gsonString: String): TreeMap<String, T> {
        return gson.fromJson(
            gsonString,
            object : TypeToken<TreeMap<String, T>>() {}.type
        )
    }

    /**
     * 将Object对象转换成json，并排除int=0的属性
     * @param o
     * @return
     */
    fun toFilterString(o: Any): String {
        val beanClass: Class<*> = o.javaClass
        return GsonBuilder()
            .addDeserializationExclusionStrategy(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    try {
                        val field = beanClass.getField(f.name)
                        field.isAccessible = true
                        val `val` = field[o] //设置此属性的值
                        val type = field.type.toString() //属性类型
                        if ("int".endsWith(type) && `val` as Int == 0) {
                            return true
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return false
                    }
                    return false
                }

                override fun shouldSkipClass(clazz: Class<*>): Boolean {
                    return false
                }
            })
            .create()
            .toJson(o)
    }

    /**
     * 字符串转Map
     */
    fun gson2Map(jsongString: String): Map<String, String> {
        return Gson().fromJson<Map<String, String>>(
            jsongString,
            object : TypeToken<Map<String, String>>() {}.type
        )
    }
}