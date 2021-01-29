package com.wind.androidlearn;

import android.content.Context;
import android.util.Log;

import com.wyt.lifecycle_annotation.AppLifeCycle;
import com.wyt.lifecycle_api.IAppLike;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/29 17:23
 */
@AppLifeCycle
class ModuleAAppLike implements IAppLike {

    @Override
    public int getPriority() {
        return NORM_PRIORITY;
    }

    @Override
    public void onCreate(Context context) {
        Log.d("AppLike", "onCreate(): this is in ModuleAAppLike.");
    }

    @Override
    public void onTerminate() {
        Log.d("AppLike", "onTerminate(): this is in ModuleAAppLike.");
    }
}
