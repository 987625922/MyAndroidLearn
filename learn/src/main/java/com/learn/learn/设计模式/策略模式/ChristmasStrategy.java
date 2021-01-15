package com.learn.learn.设计模式.策略模式;

// 圣诞节算法
public class ChristmasStrategy implements ActivityStrategy {
    @Override
    public String getActivityPrice() {
        // 经过一系列算法
        return "(圣诞节)买热干面+饮品套餐, 送大苹果一个";
    }
}
