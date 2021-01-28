package com.learn.learn.jetpack

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

/**
 * @Author: LL
 * @Description: 使用和其他在同一个activity的fragment共享的viewmodel
 * @Date:Create：in  2021/1/28 10:52
 */
class MyListFragment : Fragment() {
    private var vm: SharedViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获取viewmodel
        vm = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

    }
}