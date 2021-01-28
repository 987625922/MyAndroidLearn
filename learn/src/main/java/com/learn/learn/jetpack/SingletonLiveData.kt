package com.learn.learn.jetpack

import androidx.lifecycle.LiveData

/**
 * @Author: LL
 * @Description:全局单例的livedata，因为是全局单例，所以就能实现在多个activity和fragment之间
 * 数据共享了
 * @Date:Create：in  2021/1/28 11:03
 */
class SingletonLiveData : LiveData<String>() {
    companion object {
        val singletonLiveData = SingletonHolder.holder
    }

    /**
     * 单例内部类
     */
    private object SingletonHolder {
        val holder = SingletonLiveData()
    }

    override fun postValue(value: String?) {
        super.postValue(value)
    }

    override fun setValue(value: String?) {
        super.setValue(value)
    }
}