package com.learn.learn.设计模式.工厂方法;

public class PlumJuiceMachine implements IBeverageMachine {
    @Override
    public Drink makeDrink() {
        return new PlumJuice().make();
    }
}
