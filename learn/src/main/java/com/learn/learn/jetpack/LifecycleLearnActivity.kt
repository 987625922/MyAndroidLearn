package com.learn.learn.jetpack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.learn.learn.R
import com.wyt.common.utils.Log
import kotlinx.coroutines.launch

/**
 * @Author: LL
 * @Description: jetpack中lifecycle，livedata，viewmodel的使用
 * @Date:Create：in  2021/1/28 9:34
 */
class LifecycleLearnActivity : AppCompatActivity() {

    private val TAG = "lifecycle_learn"

    companion object {
        private fun newIntent(context: Context): Intent {
            return Intent(context, LifecycleLearnActivity::class.java)
        }

        @JvmStatic
        public fun intentTo(context: Context) {
            context.startActivity(newIntent(context))
        }
    }

    //livedata
    private val mLiveData = MutableLiveData<String>()

    /**
     * 观察多个数据 MediatorLiveData
     * 只要任何原始的 LiveData 源对象发生更改，就会触发 MediatorLiveData 对象的观察者。
     */
    private val mediatorliveData = MediatorLiveData<String>()
    private val liveData1 = MutableLiveData<String>()
    private val liveData2 = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white)
        lifecycle.addObserver(lifecycleObserver)
        //获取viewmodel
        val sharedViewModel =
                ViewModelProvider(this).get(SharedViewModel::class.java)
        mLiveData.value = "livedata test 1" //非活跃状态，不会回调onChange
        mLiveData.observe(this, Observer {
            Log.d("$TAG onChange $it")
        })
        mLiveData.value = "livedata test 2" //非活跃状态，不会回调onChange

        //添加源 livedata1
        mediatorliveData.addSource(liveData1, Observer {

        })
        //添加源 livedata2
        mediatorliveData.addSource(liveData2, {

        })
        //添加观察
        mediatorliveData.observe(this, {
            //无论livedata1还是livedata2更新，都可以接收
            Log.d("mediatorliveData $it")
        })
        //设置源livedata1
        liveData1.value = "add"
        //jetpack中快使用协程
        lifecycleScope.launch {
            whenCreated { }
            whenResumed { }
        }
    }

    override fun onStart() {
        super.onStart()
        mLiveData.value = "livedata test 3" //非活跃状态，不会回调onChange
    }

    override fun onResume() {
        super.onResume()
        Log.d("$TAG activity onResume")
        mLiveData.value = "livedata test 4" //活跃状态，回调onChange
    }

    override fun onPause() {
        super.onPause()
        Log.d("$TAG activity onPause")
        mLiveData.value = "livedata test 5" //活跃状态，回调onChange
    }

    override fun onStop() {
        super.onStop()
        mLiveData.value = "livedata test 6" //非活跃状态，不会回调onChange
    }

    override fun onDestroy() {
        super.onDestroy()
        mLiveData.value = "livedata test 2" //非活跃状态，不会回调onChange
    }

    /**
     * 监听activity生命周期的回调
     */
    val lifecycleObserver = object : LifecycleObserver {

        @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
        public fun onResume() {
            Log.d("$TAG lifecycle onResume")
        }

        @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
        public fun onPause() {
            Log.d("$TAG lifecycle onPause")
        }
    }
}