package com.wind.androidlearn.bassis.Utils;


import android.util.Log;

import com.wyt.zdf.myapplication.BuildConfig;


public class LogUtil {
    private static boolean mIsDebug = BuildConfig.DebugMode;
    private static String TAG = "MYANDROID";
    /**
     * 显示错误日志
     *
     * @param TAG     日志TAG
     * @param message 日志内容
     */
    public static void e(String TAG,String message) {
        if (mIsDebug)
            Log.e(TAG, message);
    }
    public static void e(String message) {
        if (mIsDebug)
            Log.e(TAG, message);
    }
    /**
     * 显示警告日志
     *
     * @param TAG     日志TAG
     * @param message 日志内容
     */
    public static void w(String TAG, String message) {
        if (mIsDebug)
            Log.w(TAG, message);

    }
    public static void w(String message) {
        if (mIsDebug)
            Log.w(TAG, message);
    }
    /**
     * 显示调试日志
     *
     * @param TAG     日志TAG
     * @param message 日志内容
     */
    public static void d(String TAG, String message) {
        if (mIsDebug)
            Log.d(TAG, message);

    }
    public static void d(String message) {
        if (mIsDebug)
            Log.d(TAG, message);
    }
}
