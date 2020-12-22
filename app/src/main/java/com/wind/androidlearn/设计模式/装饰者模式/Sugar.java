package com.wind.androidlearn.设计模式.装饰者模式;

public class Sugar extends Stuff {
    public Sugar(Drink originalDrink) {
        super(originalDrink);
    }

    @Override
    String stuffName() {
        return "糖";
    }
}
