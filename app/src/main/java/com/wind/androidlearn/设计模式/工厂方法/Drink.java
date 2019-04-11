package com.wind.androidlearn.设计模式.工厂方法;

public abstract class Drink {
    private String name;
    private String instantPackage;

    public Drink make() {
        this.name = getName();
        this.instantPackage = getInstantPackage();
        return this;
    }

    abstract String getInstantPackage();
    abstract String getName();

    @Override
    public String toString() {
        return "This is a cup of:" + this.name;
    }
}
