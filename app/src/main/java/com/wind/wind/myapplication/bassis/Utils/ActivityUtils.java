package com.library.util;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {
    /**
     * 判断activity中是否包含目标fragment
     *
     * @param fragmentManager
     * @param fragmentContainerId
     * @param fragmentClazz       目标fragment类型
     * @return
     */
    public static boolean hasFragment(@NonNull FragmentManager fragmentManager, @IdRes int fragmentContainerId, Class fragmentClazz) {
        Fragment fragment = fragmentManager.findFragmentById(fragmentContainerId);
        return fragment != null && fragment.getClass() == fragmentClazz;
    }

    /**
     * 添加fragment到activity
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        fragmentManager.beginTransaction()
                .add(frameId, fragment)
                .commit();
    }

    /**
     * 通过context获取activity
     *
     * @param context
     * @return
     */
    public static Activity getActivity(@Nullable Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * 从LifecycleOwner获取Context
     *
     * @param owner
     * @return
     */
    public static Context getContext(@Nullable LifecycleOwner owner) {
        if (owner instanceof Context) {
            return (Context) owner;
        } else if (owner instanceof Fragment) {
            return ((Fragment) owner).getContext();
        }
        return null;
    }

    /**
     * 判断context是否有效
     *
     * @param context
     * @return
     */
    public static boolean isContextValid(@Nullable Context context) {
        return isValid(getActivity(context));
    }

    public static boolean isValid(@Nullable Activity activity) {
        return activity != null && !activity.isFinishing();
    }

    /**
     * 回到桌面
     */
    public static void startHomeActivity(@NonNull Context context) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(homeIntent);
    }

    /**
     * 设置屏幕常亮
     *
     * @param activity
     */
    public static void setScreenOn(@NonNull Activity activity) {
        activity.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    /**
     * 退出页面
     *
     * @param fragment
     */
    public static boolean finish(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        return finish(activity);
    }

    public static boolean finish(@Nullable Activity activity) {
        if (isValid(activity)) {
            activity.finish();
            return true;
        }
        return false;
    }
}