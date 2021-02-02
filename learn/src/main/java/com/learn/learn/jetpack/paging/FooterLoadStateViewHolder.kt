package com.learn.learn.jetpack.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.learn.learn.R
import com.learn.learn.databinding.AdapterPagingFooterBinding

/**
 * @Author: LL
 * @Description: paging的脚布局
 * @Date:Create：in  2021/1/28 16:35
 */
class FooterLoadStateViewHolder(private val binding: AdapterPagingFooterBinding,
                                retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.tvFooter.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvFooter.text = loadState.error.localizedMessage
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): FooterLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_paging_footer, parent, false)
            val binding = AdapterPagingFooterBinding.bind(view)
            return FooterLoadStateViewHolder(binding, retry)
        }
    }

}