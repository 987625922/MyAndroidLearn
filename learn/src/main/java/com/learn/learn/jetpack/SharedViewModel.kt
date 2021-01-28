package com.learn.learn.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: LL
 * @Description: 在同一个activity的fragment的viewmodel共享
 * @Date:Create：in  2021/1/28 10:50
 */
class SharedViewModel:ViewModel() {
    private val shatedStr = MutableLiveData<String>()

}