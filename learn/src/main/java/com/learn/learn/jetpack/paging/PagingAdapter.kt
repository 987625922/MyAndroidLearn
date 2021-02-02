package com.learn.learn.jetpack.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.learn.learn.databinding.AdapterPagingBinding

/**
 * @Author: LL
 * @Description:pagingAdapter的使用
 * @Date:Create：in  2021/1/28 15:48
 */
class PagingAdapter constructor(val context: Context) : PagingDataAdapter<String, PagingAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val str = getItem(position)
        holder.apply {
            bind(onCreateListener(position), str)
        }
    }

    /**
     * Holder的点击事件
     */
    private fun onCreateListener(id: Int): View.OnClickListener {
        return View.OnClickListener {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(context, AdapterPagingBinding.inflate(LayoutInflater.from(parent.context)
                , parent, false))
    }

    class ViewHolder(
            val context: Context,
            private val binding: AdapterPagingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: String?) {
            binding.apply {
                this.tvName.setOnClickListener(listener)
                this.tvName.text = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
            oldItem: String,
            newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
            oldItem: String,
            newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}