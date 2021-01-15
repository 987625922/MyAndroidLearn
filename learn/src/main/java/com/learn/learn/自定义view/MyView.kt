package com.learn.learn.自定义view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.learn.learn.R

/**
 * @Author: LL
 * @Description:自定义view
 * @Date:Create：in 2020/12/3 14:38
 */
internal class MyView : View {
    private val DEFAULT_SIZE = 450

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.MyView)
        val mName = ta?.getString(R.styleable.MyView_name)
        ta?.recycle()
    }

    //onMeasure方法的作用是测量控件的大小。当控件的父控件要放置该控件的时候，
    // 父控件会调用子控件的onMeasure方法询问子控件：
    // “你有多大的尺寸，我要给你多大的地方才能容纳你？”，
    // 然后传入两个参数（widthMeasureSpec和heightMeasureSpec），
    // 这两个参数就是父控件告诉子控件可获得的空间以及关于这个空间的约束条件，
    // 子控件拿着这些条件就能正确的测量自身的宽高了。
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Int = getMySize(widthMeasureSpec)
        val height: Int = getMySize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    //感觉测量完成的数据进行定位
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    //绘画到页面上
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    /**
     * 获取测量大小
     */
    private fun getMySize(measureSpec: Int): Int {
        val result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize //确切大小,所以将得到的尺寸给view
        } else if (specMode == MeasureSpec.AT_MOST) {
            //默认值为450px,此处要结合父控件给子控件的最多大小(要不然会填充父控件),
            // 所以采用最小值
            result = Math.min(DEFAULT_SIZE, specSize)
        } else {
            result = DEFAULT_SIZE
        }
        return result
    }
    /**
     * 在Activity中获取宽高
     * =====
     * 在onCreate , onResume等方法中获取到的都是0
     * ===
     *   view.post(new Runnable() {
    @Override
    public void run() {
    int width = view.getMeasuredWidth();
    int height = view.getMeasuredHeight();
    }
    });
     */
}