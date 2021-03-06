package com.learn.learn.设计模式.single;
/**
 *
 *  单例实现
 */
public class SingleSy {
    private SingleSy() {
    }

    private static volatile SingleSy singleSy;

    public static SingleSy getInstance() {
        if (singleSy == null) {
            synchronized (SingleSy.class) {
                if (singleSy == null) {
                    singleSy = new SingleSy();
                }
            }
        }
        return singleSy;
    }
}
