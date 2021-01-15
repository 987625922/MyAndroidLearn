package com.wyt.common.utils

import android.widget.ImageView

/**
 * @Author: LL
 * @Description: imageView工具类
 * @Date:Create：in 2020/7/7 11:33
 */
object ImageViewUtil {
    /**
     * 获取imageView图片宽高
     *
     * @param imageview
     * @return 0:宽 1：高
     */
    fun getRealImgShowSize(imageview: ImageView): IntArray {
        val rect = imageview.drawable.bounds
        //可见image的宽高
        val scaledHeight = rect.height()
        val scaledWidth = rect.width()
        //获得ImageView中Image的变换矩阵
        val matrix = imageview.imageMatrix
        val values = FloatArray(10)
        matrix.getValues(values)
        //Image在绘制过程中的变换矩阵，从中获得x和y方向的缩放系数
        val sx = values[0]
        val sy = values[4]
        //计算Image在屏幕上实际绘制的宽高
        val realImgShowWidth = (scaledWidth * sx).toInt()
        val realImgShowHeight = (scaledHeight * sy).toInt()
        return intArrayOf(realImgShowWidth, realImgShowHeight)
    }
}