package com.wind.androidlearn.设计模式.策略模式;

// 默认算法(注意这个, 稍后的扩展阅读会说下这个Default实现的意义)
public class DefaultActivityStrategy implements ActivityStrategy {
    @Override
    public String getActivityPrice() {
        // 什么都不做
        return "没有活动";
    }
}
