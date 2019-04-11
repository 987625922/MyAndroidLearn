package com.wind.androidlearn.设计模式.策略模式;

public class ThanksGivingDayStrategy implements ActivityStrategy {
    @Override
    public String getActivityPrice() {
        // 经过一系列算法
        return "(感恩节)所有饮品一律5折";
    }
}
