package com.wind.androidlearn.设计模式.策略模式;

// 收银台
public class Checkstand {

    private ActivityStrategy mActivityStrategy;

    public Checkstand() {
        mActivityStrategy = new DefaultActivityStrategy();
    }

    public Checkstand(ActivityStrategy activityStrategy) {
        this.mActivityStrategy = activityStrategy;
    }

    public void setActivityStrategy(ActivityStrategy activityStrategy) {
        this.mActivityStrategy = activityStrategy;
    }

    public void printBill() {
        System.out.println("本次账单活动:" + mActivityStrategy.getActivityPrice());
    }

    public static void main(String[] args) {

        // 收银台, 默认
        Checkstand checkstand = new Checkstand();
        checkstand.printBill();

        // 感恩节期间
        checkstand.setActivityStrategy(new ThanksGivingDayStrategy());
        checkstand.printBill();

        // 双十二
        checkstand.setActivityStrategy(new DoubleTwelveDayStrategy());
        checkstand.printBill();

        // 圣诞节期间
        checkstand.setActivityStrategy(new ChristmasStrategy());
        checkstand.printBill();
    }
}
