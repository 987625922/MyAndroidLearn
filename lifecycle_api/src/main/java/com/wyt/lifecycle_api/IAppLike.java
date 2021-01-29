package com.wyt.lifecycle_api;

import android.content.Context;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/29 17:17
 */
public interface IAppLike {

    int MAX_PRIORITY = 10;
    int MIN_PRIORITY = 1;
    int NORM_PRIORITY = 5;

    int getPriority();
    void onCreate(Context context);
    void onTerminate();
}
