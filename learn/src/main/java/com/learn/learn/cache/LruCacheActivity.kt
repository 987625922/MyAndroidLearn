package com.learn.learn.cache

import android.graphics.Bitmap
import androidx.collection.LruCache
import com.wyt.common.base.BaseActivity

/**
 * @Author: LL
 * @Description:LruCache使用
 * @Date:Create：in 2021/1/22 16:26
 */
internal class LruCacheActivity : BaseActivity() {

    override fun getLayout(): Int {
        return 0
    }

    override fun initView() {}
    override fun initData() {
        /**
         * 重写sizeOf方法，设置每个存储对象在内存中的大小
         */
        val lruCache = object : LruCache<Int, Bitmap>(8) {
            override fun sizeOf(key: Int, value: Bitmap): Int {
                return value.width * value.height
            }
        }
        var tempBm50 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        lruCache.put(0, tempBm50)
        var tempBm40 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
        lruCache.put(0, tempBm40)
    }

    override fun start() {}
}