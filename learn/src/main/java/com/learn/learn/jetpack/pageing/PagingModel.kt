package com.learn.learn.jetpack.pageing

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2021/1/28 15:42
 */
class PagingModel : ViewModel() {
    var strs = Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
    ), pagingSourceFactory = { CustomPageDataSource() }).flow

}