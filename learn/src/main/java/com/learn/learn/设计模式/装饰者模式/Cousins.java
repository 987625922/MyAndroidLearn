package com.learn.learn.设计模式.装饰者模式;

/**
 * @Description 允许向一个现有的对象添加新的功能，同时又不改变其结构
 * @author Administrator
 */
public class Cousins {
    public static void main(String[] args) {
        Drink drink = new Ice(new Sugar(new Coke()));
        System.out.println(drink.make());
    }
}
