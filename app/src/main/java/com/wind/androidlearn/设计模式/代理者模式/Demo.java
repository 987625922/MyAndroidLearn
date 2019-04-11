package com.wind.androidlearn.设计模式.代理者模式;

public class Demo {
    public static void main(String[] args) {
        DaLong daLong = new DaLong(new XiaoGuang());
        // 第一轮, 对方报价120.
        daLong.signing(120);
        // 第二轮, 对方报价100.
        daLong.signing(100);
        // 第三轮, 对方报价90.
        daLong.signing(90);
    }
}