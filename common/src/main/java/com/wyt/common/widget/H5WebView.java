package com.wyt.common.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * create by LL
 */
public class H5WebView extends android.webkit.WebView {
    public H5WebView(Context context) {
        super(context);
        this.getSettings().setAllowFileAccess(false);
    }

    public H5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.getSettings().setAllowFileAccess(false);
    }

    public H5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.getSettings().setAllowFileAccess(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

}
