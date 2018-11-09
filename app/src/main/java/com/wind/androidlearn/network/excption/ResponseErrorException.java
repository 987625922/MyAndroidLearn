package com.wind.androidlearn.network.excption;

/**
 * 服务器返回数据时，返回码不等于200的异常
 */
public class ResponseErrorException extends RuntimeException{
    public int errorCode;
    public String msg;
    public String name;

    public ResponseErrorException(String name, String errorMessage,int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.msg = errorMessage;
        this.name = name;
    }

}
