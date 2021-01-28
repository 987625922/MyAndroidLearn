package com.learn.learn.jetpack.pageing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.rlMain.layoutManager = LinearLayoutManager(this)
        mBinding.rlMain.adapter = adapter

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