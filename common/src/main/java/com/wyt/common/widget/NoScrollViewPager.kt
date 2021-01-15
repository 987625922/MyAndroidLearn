package com.wyt.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * 不可滑动且viewpager
 */
class NoScrollViewPager constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : ViewPager(context!!, attrs), OnPageChangeListener {
    //是否开启动画
    private var isAnimate = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun onPageScrolled(i: Int, v: Float, i1: Int) {
        super.onPageScrolled(i, v, i1)
    }

    override fun onPageSelected(i: Int) {
        currentItem = i
    }

    override fun onPageScrollStateChanged(i: Int) {}

    /**
     * 处理在tv使用时，当按键下一个view不在viewpager的时候，不进行翻页
     * @param event
     * @return
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return false
    }

    fun setAnimate(animate: Boolean) {
        isAnimate = animate
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, isAnimate)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }
}