package com.learn.learn.jetpack.pageing

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * @Author: LL
 * @Description:paging脚布局的adapter
 * @Date:Create：in  2021/1/29 14:24
 */
class FooterLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterLoadStateViewHolder {
        return FooterLoadStateViewHolder.create(parent, retry)
    }
}