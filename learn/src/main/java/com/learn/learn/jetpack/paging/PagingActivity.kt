package com.learn.learn.jetpack.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.learn.R
import com.learn.learn.databinding.ActivityPagingBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @Author: LL
 * @Description: paging3使用的activity
 * @Date:Create：in  2021/1/28 16:16
 */
class PagingActivity : AppCompatActivity() {

    private val mBinding: ActivityPagingBinding by lazy {
        DataBindingUtil.setContentView<ActivityPagingBinding>(
                this,
                R.layout.activity_paging
        )
    }

    //viewModels
    private val vm by lazy {
        ViewModelProviders.of(this, PagingModelFactory())
                .get(PagingModel::class.java)
    }

    private val adapter by lazy {
        PagingAdapter(this)
    }

    private var job: Job? = null

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.rlMain.layoutManager = LinearLayoutManager(this)
        //设置adapter并带上另一个布局
        mBinding.rlMain.adapter = adapter.withLoadStateFooter(footer = FooterLoadStateAdapter { adapter.retry() })
//        mBinding.rlMain.adapter.notifyDataSetChanged()
        /**
         * 这个方法是当新的PagingData被提交并且显示的回调
         */
        adapter.addDataRefreshListener {

        }
        /**
         * 监听adapter状态
         */
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {

                }
                is LoadState.NotLoading -> {

                }
                is LoadState.Error -> {

                }
            }
        }
        job = vm.viewModelScope.launch {
            vm.strs.collect {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}