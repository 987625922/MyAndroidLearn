package com.wind.wind.myapplication.bassis.Utils;

import android.util.Log;

import com.wyt.zdf.myapplication.BuildConfig;


public class LogUtil {
    private static boolean mIsDebug = BuildConfig.DebugMode;

    /**
     * 显示错误日志
     *
     * @param TAG     日志TAG
     * @param message 日志内容
     */
    public void error(String TAG, final String message) {
        if (mIsDebug)
            Log.e(TAG, message);
    }

    /**
     * 显示警告日志
     *
     * @param TAG     日志TAG
     * @param message 日志内容
     */
    public void warning(final String TAG, final String message) {
        if (mIsDebug)
            Log.w(TAG, message);

    }

    /**
     * 显示调试日志
     *
     * @param TAG     日志TAG
     * @param message 日志内容
     */
    public void debug(final String TAG, final String message) {
        if (mIsDebug)
            Log.d(TAG, message);

    }

}
