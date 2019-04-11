package com.wind.androidlearn.设计模式.装饰模式;

public class Cousins {
    public static void main(String[] args) {
        Drink drink = new Ice(new Sugar(new Coke()));
        System.out.println(drink.make());
    }
}
