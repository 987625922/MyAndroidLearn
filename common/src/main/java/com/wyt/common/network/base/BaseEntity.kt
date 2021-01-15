package com.wyt.common.network.base

/**
 * @Description:1基础实体类
 * 机器人后台返回的base实体
 */
data class BaseEntity<T>(val code: Int, val msg: String)