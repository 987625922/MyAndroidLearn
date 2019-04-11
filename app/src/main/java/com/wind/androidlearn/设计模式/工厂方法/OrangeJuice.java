package com.wind.androidlearn.设计模式.工厂方法;

public class OrangeJuice extends Drink {
    @Override
    String getInstantPackage() {
        return "速溶橙汁粉";
    }

    @Override
    String getName() {
        return "橙汁";
    }
}
