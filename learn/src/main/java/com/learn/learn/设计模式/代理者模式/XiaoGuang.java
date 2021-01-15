package com.learn.learn.设计模式.代理者模式;

// 心底发虚的小光:
public class XiaoGuang implements Person {
    @Override
    public void signing(int price) {
        System.out.println("小光以" + price + "每箱的价格签单.");
    }
}
