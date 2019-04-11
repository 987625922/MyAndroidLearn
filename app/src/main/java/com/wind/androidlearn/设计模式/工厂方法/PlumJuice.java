package com.wind.androidlearn.设计模式.工厂方法;

public class PlumJuice extends Drink {
    @Override
    String getInstantPackage() {
        return "速溶酸梅粉";
    }

    @Override
    String getName() {
        return "酸梅汤";
    }
}