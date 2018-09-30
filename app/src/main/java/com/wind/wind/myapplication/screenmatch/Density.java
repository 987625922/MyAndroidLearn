package com.wind.wind.myapplication.screenmatch;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

public class Density  {

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
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
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
