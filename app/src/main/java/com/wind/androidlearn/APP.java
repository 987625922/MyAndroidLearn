package com.wind.androidlearn;
/**
 * Created by Administrator on 2018/8/31 0031.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.bugly.crashreport.CrashReport;
import com.wind.androidlearn.bassis.Utils.screenmatch.Density;

/**
 * 类 名： APP<br>
 * 说 明：<br>
 * 创建日期：2018/8/31 0031<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * 待做事情：
 */
public class APP extends Application {

    private static APP application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        //MultiDex分包方法 必须最先初始化
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //头条适配绑定
        Density.setDensity(this);

        CrashReport.initCrashReport(getApplicationContext(), "1", false);
        //在application中监听每一个activity的生命周期
//        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            public void onActivityStopped(Activity activity) {
//            }
//
//            public void onActivityStarted(Activity activity) {
//            }
//
//            @Override
//            public void onActivityCreated(Activity arg0, Bundle arg1) {
//                // TODO Auto-generated method stub
//                Log.e("--->", "onActivityCreated");
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity arg0) {
//                // TODO Auto-generated method stub
//                Log.e("--->", "onDestroyed");
//            }
//
//            @Override
//            public void onActivityPaused(Activity arg0) {
//                // TODO Auto-generated method stub
//                Log.e("--->", "onPause");
//            }
//
//            @Override
//            public void onActivityResumed(Activity arg0) {
//                // TODO Auto-generated method stub
//                Log.d("application", "---- onActivityResumed: " + arg0.toString());
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
//                // TODO Auto-generated method stub
//
//            }
//        });

        if (true) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(application); // As early as possible, it is recommended to initialize in the Application



    }


}
