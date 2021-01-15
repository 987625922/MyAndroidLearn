package com.wind.androidlearn.bassis.Utils;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import android.widget.Toast;

import com.wyt.zdf.myapplication.BuildConfig;

public final class ToastUtils {

    private static Toast toast;

    private ToastUtils() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    private static Toast getToast(Context context) {
        if (context == null) {
            return null;
        }
        if (toast == null) {
            Context applicationContext = context.getApplicationContext();
            toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT);
        }
        return toast;
    }

    @UiThread
    public static void showShort(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    public static void showShortInMainThread(Context context, String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> showShort(context, message));
    }

    public static void debugToast(Context context, String msg) {
        if (BuildConfig.DEBUG) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> showShort(context, msg));
        }
    }

    @UiThread
    public static void showShort(Context context, @StringRes int message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    @UiThread
    public static void showLong(Context context, String message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    @UiThread
    public static void showLong(Context context, @StringRes int message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    private static void show(Context context, String message, int duration) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setText(message);
            toast.setDuration(duration);
            toast.show();
        }
    }

    private static void show(Context context, @StringRes int messageRes, int duration) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setText(messageRes);
            toast.setDuration(duration);
            toast.show();
        }
    }
}