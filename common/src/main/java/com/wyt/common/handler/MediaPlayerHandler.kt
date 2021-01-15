package com.wyt.common.handler

import android.media.MediaPlayer
import android.view.SurfaceHolder
import java.io.IOException


/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2020/10/16 14:26
 */
class MediaPlayerHandler :
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {
    var onCompletionListener: OnCompletionListener? = null
    private var mPlayer: MediaPlayer? = null
    private var hasPrepared = false


    private fun initIfNecessary() {
        if (null == mPlayer) {
            mPlayer = MediaPlayer()
            mPlayer!!.setOnErrorListener(this)
            mPlayer!!.setOnCompletionListener(this)
            mPlayer!!.setOnPreparedListener(this)
        }
    }

    fun play(dataSource: String) {
        hasPrepared = false // 开始播放前讲Flag置为不可操作
        initIfNecessary() // 如果是第一次播放/player已经释放了，就会重新创建、初始化
        try {
            mPlayer!!.reset()
            mPlayer!!.setDataSource(dataSource) // 设置曲目资源
            mPlayer!!.prepareAsync() // 异步的准备方法
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun start() {
        // release()会释放player、将player置空，所以这里需要判断一下
        if (null != mPlayer && hasPrepared) {
            mPlayer!!.start()
        }
    }

    fun pause() {
        if (null != mPlayer && hasPrepared) {
            mPlayer!!.pause()
        }
    }

    fun seekTo(position: Int) {
        if (null != mPlayer && hasPrepared) {
            mPlayer!!.seekTo(position)
        }
    }

    // 对于播放视频来说，通过设置SurfaceHolder来设置显示Surface。这个方法不需要判断状态、
    // 也不会改变player状态
    fun setDisplay(holder: SurfaceHolder?) {
        if (null != mPlayer) {
            mPlayer!!.setDisplay(holder)
        }
    }

    fun release() {
        hasPrepared = false
        mPlayer?.stop()
        mPlayer?.release()
        mPlayer = null
    }


    override fun onPrepared(p0: MediaPlayer?) {
        hasPrepared = true
        start()
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        hasPrepared = false
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {
        hasPrepared = false
        onCompletionListener?.onCompletion(p0)
    }

    interface OnCompletionListener {
        fun onCompletion(p0: MediaPlayer?)
    }
}