package com.learn.learn.设计模式.装饰者模式;

public abstract class Stuff implements Drink {

    private Drink originalDrink;
    public Stuff(Drink originalDrink) {
        this.originalDrink = originalDrink;
    }

    @Override
    public String make() {
        return originalDrink.make() + ", 加一份" + stuffName();
    }

    abstract String stuffName();
}