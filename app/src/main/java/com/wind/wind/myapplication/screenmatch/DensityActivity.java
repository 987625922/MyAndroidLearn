package com.wind.wind.myapplication.screenmatch;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.wyt.zdf.myapplication.R;

public class DensityActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setCustomDensity(this,this.getApplication());
        setOrientation();
        setContentView(R.layout.activity_density);


    }
    //改版今日头条适配
    public void setOrientation() {
        Density.setDefault(this);
    }

    //今日头条适配
    private static float sRoncompatDennsity;
    private static float sRoncompatScaledDensity;
    private void setCustomDensity(@NonNull Activity activity, final @NonNull Application application) {

        //application
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sRoncompatDennsity == 0) {
            sRoncompatDennsity = appDisplayMetrics.density;
            sRoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sRoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //计算宽为360dp 同理可以设置高为640dp的根据实际情况
        final float targetDensity = appDisplayMetrics.heightPixels / 640;//横屏
//        final float targetDensity = appDisplayMetrics.widthPixels / 360;  //竖屏
        final float targetScaledDensity = targetDensity * (sRoncompatScaledDensity / sRoncompatDennsity);
        final int targetDensityDpi = (int) (targetDensity * 160);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        appDisplayMetrics.scaledDensity = targetScaledDensity;

        //activity
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();

        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
    }


}
