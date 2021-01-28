package com.learn.learn.jetpack.pageing

import androidx.paging.PagingSource

/**
 * @Author: LL
 * @Description:paging3的数据源
 * @Date:Create：in  2021/1/28 14:41
 */
class CustomPageDataSource() : PagingSource<Int,String>() {
    private val SHOE_START_INDEX = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val pos = params.key ?: SHOE_START_INDEX
        val startIndex = pos * params.loadSize + 1
        val endIndex = (pos + 1) * params.loadSize
        return try {
            // 从数据库拉去数据
            val strs = mutableListOf<String>()
            // 返回你的分页结果，并填入前一页的 key 和后一页的 key
            LoadResult.Page(
                    strs,
                    if (pos <= SHOE_START_INDEX) null else pos - 1,
                    if (strs.isNullOrEmpty()) null else pos + 1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}