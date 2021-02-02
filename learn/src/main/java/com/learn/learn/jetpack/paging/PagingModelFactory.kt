package com.learn.learn.jetpack.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2021/1/28 16:20
 */
class PagingModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PagingModel() as T
    }
}