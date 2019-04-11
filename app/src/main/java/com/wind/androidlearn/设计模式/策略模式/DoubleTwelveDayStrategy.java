package com.wind.androidlearn.设计模式.策略模式;

// 双十二算法
public class DoubleTwelveDayStrategy implements ActivityStrategy {

    @Override
    public String getActivityPrice() {
        // 经过一系列算法
        return "(双十二)满12立减2元";
    }
}
