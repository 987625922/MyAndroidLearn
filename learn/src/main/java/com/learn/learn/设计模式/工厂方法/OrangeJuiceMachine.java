package com.learn.learn.设计模式.工厂方法;


public class OrangeJuiceMachine implements IBeverageMachine {
    @Override
    public Drink makeDrink() {
        return new OrangeJuice().make();
    }
}
