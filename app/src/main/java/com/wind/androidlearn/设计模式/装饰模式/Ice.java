package com.wind.androidlearn.设计模式.装饰模式;

public class Ice extends Stuff {
    public Ice(Drink originalDrink) {
        super(originalDrink);
    }
    @Override
    String stuffName() {
        return "冰";
    }
}
