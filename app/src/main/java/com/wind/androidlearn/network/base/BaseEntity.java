package com.wind.androidlearn.network.base;

/**
 * 基础实体类
 */
public class BaseEntity<E> {

    //返回码
    public int code;
    //描述
    public String msg;
    //数据
    public E data;

    /**
     * 判断请求是否成功，默认成功码 200
     */
    public boolean isSuccess(){
        return code == 200;
    }

    /**
     * 判断请求是否成功,通过指定的code
     */
    public boolean isSuccess(int code){
        return this.code == code;
    }
}
