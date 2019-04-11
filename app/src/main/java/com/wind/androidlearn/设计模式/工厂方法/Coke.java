package com.wind.androidlearn.设计模式.工厂方法;

public class Coke extends Drink {
    @Override
    String getInstantPackage() {
        return "速溶可乐粉";
    }

    @Override
    String getName() {
        return "可乐";
    }
}