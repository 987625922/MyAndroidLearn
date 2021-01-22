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
import com.wyt.common.AppConfig;
import com.wyt.common.BuildConfig;
import com.wyt.common.base.BaseApplication;
import com.wyt.common.utils.screenmatch.Density;

import org.jetbrains.annotations.NotNull;

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

        // 初始化 ARouter
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);

        initModuleApp(this);
        initModuleData(this);
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


    @Override
    public void initModuleApp(@NotNull Application application) {
        for (String moduleApp : AppConfig.moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.initModuleApp(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initModuleData(@NotNull Application application) {
        for (String moduleApp : AppConfig.moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.initModuleData(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
