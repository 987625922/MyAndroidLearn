package com.wyt.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wyt.common.R
import com.wyt.common.utils.ScreenUtil.dip2px
import com.wyt.common.utils.glide.RoundedCornersTransformation

/**
 * 图片加载工具类
 */
object GlideUtil {
    /**
     * 有默认图的请求设置
     */
    private val requestOptions: RequestOptions by lazy {
        RequestOptions()
        requestOptions
            .error(R.mipmap.loading)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.loading)
            .format(DecodeFormat.PREFER_RGB_565)
            .fitCenter()
            .encodeFormat(Bitmap.CompressFormat.JPEG)
    }

    /**
     * 加载网络图片
     */
    fun load(context: Context, view: ImageView, url: String) {
        if (url.isNotEmpty())
            Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(view)
    }


    /**
     * 加载应用图片
     */
    fun load(context: Context, view: ImageView, id: Int) {
        Glide.with(context)
            .load(id)
            .into(view)
    }

    /**
     * 加载本地的图片
     */
    fun loadLocal(context: Context, view: ImageView, url: String, radius: Float) {
        if (url.isNotEmpty())
            Glide.with(context)
                .load(url)
                .transform(RoundedCornersTransformation(dip2px(context, radius), 0))
                .into(view)
    }

    /**
     * 加载本地的图片
     */
    fun loadLocal(context: Context, view: ImageView, bitmap: Bitmap, radius: Float) {
        bitmap?.let {
            Glide.with(context)
                .load(it)
                .transform(RoundedCornersTransformation(dip2px(context, radius), 0))
                .into(view)

        }
    }

    fun load(context: Context, view: ImageView, url: String, radius: Float) {
        if (url.isNotEmpty())
            Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .transform(RoundedCornersTransformation(dip2px(context, radius), 0))
                .into(view)
    }

    fun load(context: Context, view: ImageView, id: Int, radius: Float) {
        Glide.with(context)
            .load(id)
            .transform(RoundedCornersTransformation(dip2px(context, radius), 0))
            .into(view)
    }

}