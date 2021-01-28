package com.learn.learn.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.learn.learn.R
import com.wyt.common.utils.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

/**
 * @Author: LL
 * @Description:协程的使用
 * @Date:Create：in  2021/1/28 13:39
 */
class CoroutinesActivity : AppCompatActivity() {
    /**
     * 创建一个Mainscope
     */
    val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white)
        //启动协程
        scope.launch {
            //async能够并发执行任务
            val one = async { "1" }
            val two = async { "2" }
            Log.d(one.await() + two.await())
        }
        //flow 流
        lifecycleScope.launch {
            createFlow()
                    .flowOn(Dispatchers.IO)                    //可选 消费者线程

                    .catch {
                        //异常捕获

                    }
                    .onCompletion {
                        //完成监听

                    }
                    .collect {
                        //buffer	数据发射并发，collect 不并发
                        //conflate	发射数据太快，只处理最新发射的
                        //collectLatest	接收处理太慢，只处理最新接收的
                        //zip 组合两个流，双方都有新数据才会发射处理
                        //combine 组合两个流，在经过第一次发射以后，任意方有新数据来的时候就可以发射，另一方有可能是已经发射过的数据

                        Log.d(it.toString())
                    }
        }
        //Channel是一个面向多协程之间数据传输的 BlockQueue
        lifecycleScope.launch {
            val channel = Channel<Int>()
            launch {
                for (i in 1..5) {
                    delay(200)
                    channel.send(i * i)
                }
                channel.close()
            }
            launch {
                for (y in channel) {
                    Log.d(y.toString())
                }
            }
        }
    }

    private fun createFlow(): Flow<Int> = flow {
        for (i in 1..10) {
            emit(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //销毁时释放协程
        scope.cancel()
    }
}