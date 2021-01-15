package com.wyt.common.network.excption

/**
 * 后台返回的错误封装
 */
class ApiException(var msg: String, var errorCode: Int) :
    RuntimeException(msg)