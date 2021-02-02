package com.learn.learn.jetpack.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2021/1/28 15:42
 */
class PagingModel : ViewModel() {
    /**
     * cacheIn对数据进行缓存
     */
    var strs = Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
    ), pagingSourceFactory = { CustomPageDataSource() }).flow.cachedIn(viewModelScope)

}