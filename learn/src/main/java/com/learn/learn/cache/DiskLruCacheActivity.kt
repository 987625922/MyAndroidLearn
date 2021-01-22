package com.learn.learn.cache

import android.content.Context
import android.content.Intent
import android.os.Environment
import com.jakewharton.disklrucache.DiskLruCache
import com.learn.learn.R
import com.wyt.common.base.BaseActivity
import com.wyt.common.utils.Log
import java.io.File

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2021/1/22 16:36
 */
class DiskLruCacheActivity : BaseActivity() {
    companion object {
        private fun newIntent(context: Context): Intent? {
            return Intent(context, DiskLruCacheActivity::class.java)
        }

        @JvmStatic
        fun intentTo(context: Context) {
            context.startActivity(newIntent(context))
        }
    }

    private val MAX_SIZE = 10 * 1024 * 1024L
    private var diskLruCache: DiskLruCache? = null
    override fun start() {
    }

    override fun initData() {
        initDiskLruCache()
        //填写value的key
        val editor = diskLruCache?.edit("key")
        if (editor != null) {
            val outputStream = editor.newOutputStream(0)
            outputStream.write(1)
            editor.commit()
            diskLruCache?.flush()
        }
        val snapshot = diskLruCache?.get("key")
        if (snapshot != null) {
            val inputStream = snapshot.getInputStream(0)
            val srt = inputStream.read().toString()
            Log.d("DiskLruCache =======> $srt")
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_white
    }

    override fun initView() {
    }

    fun initDiskLruCache() {
        if (diskLruCache == null || diskLruCache?.isClosed!!) {
            val cacheDir = getDiskCacheDir(this, "CacheDir")
            if (!cacheDir?.exists()!!) {
                cacheDir.mkdirs()
            }
            diskLruCache = DiskLruCache.open(cacheDir, 1, 1, MAX_SIZE)
        }
    }

    /**
     * 获取SD卡的路径
     */
    fun getDiskCacheDir(context: Context, uniqueName: String): File? {
        return File(Environment.getExternalStorageDirectory().absolutePath + File.separator + uniqueName)
    }
}