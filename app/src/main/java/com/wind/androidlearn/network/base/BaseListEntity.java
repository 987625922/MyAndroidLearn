package com.wind.androidlearn.network.base;

import java.util.List;

/**
 * 基础实体类 - 带列表
 */
public class BaseListEntity<E> {

    //返回码
    public int code;
    //描述
    public String msg;
    //数据
    public Data data;

    /**
     * 判断请求是否成功，默认成功码 200
     */
    public boolean isSuccess() {
        return code == 200;
    }

    /**
     * 判断请求是否成功,通过指定的code
     */
    public boolean isSuccess(int code) {
        return this.code == code;
    }

    /**
     * 内部列表类
     */
    public class Data {
        public int total;
        public List<E> list;
    }
}
