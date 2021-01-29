package com.wyt.lifecycle_api;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/29 17:17
 */
public class AppLifeCycleManager {

    private static List<IAppLike> APP_LIKE_LIST = new ArrayList<>();

    /**
     * 注册IAppLike类
     */
    public static void registerAppLike(IAppLike appLike) {
        APP_LIKE_LIST.add(appLike);
    }

    /**
     * 初始化，需要在Application.onCreate()里调用
     *
     * @param context
     */
    public static void init(Context context) {
        Collections.sort(APP_LIKE_LIST, new AppLikeComparator());
        for (IAppLike appLike : APP_LIKE_LIST) {
            appLike.onCreate(context);
        }
    }

    public static void terminate() {
        for (IAppLike appLike : APP_LIKE_LIST) {
            appLike.onTerminate();
        }
    }

    /**
     * 优先级比较器，优先级大的排在前面
     */
    static class AppLikeComparator implements Comparator<IAppLike> {

        @Override
        public int compare(IAppLike o1, IAppLike o2) {
            int p1 = o1.getPriority();
            int p2 = o2.getPriority();
            return p2 - p1;
        }
    }
}
