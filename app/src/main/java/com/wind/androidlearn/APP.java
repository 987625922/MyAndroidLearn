package com.wind.androidlearn;
/**
 * Created by Administrator on 2018/8/31 0031.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.wyt.common.base.BaseApplication;
import com.wyt.common.utils.screenmatch.Density;

/**
 * 类 名： APP<br>
 * 说 明：<br>
 * 创建日期：2018/8/31 0031<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * 待做事情：
 */
public class APP extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex分包方法 必须最先初始化
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //头条适配绑定
        Density.setDensity(this);

        //在application中监听每一个activity的生命周期
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity arg0, Bundle arg1) {
                Log.e("--->", "onActivityCreated");
            }

            @Override
            public void onActivityDestroyed(Activity arg0) {
                Log.e("--->", "onDestroyed");
            }

            @Override
            public void onActivityPaused(Activity arg0) {
                Log.e("--->", "onPause");
            }

            @Override
            public void onActivityResumed(Activity arg0) {
                Log.d("application", "---- onActivityResumed: " + arg0.toString());
            }

            @Override
            public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {

            }
        });
    }

}
