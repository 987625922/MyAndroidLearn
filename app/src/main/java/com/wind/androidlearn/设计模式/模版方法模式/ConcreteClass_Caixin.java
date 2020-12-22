package com.wind.androidlearn.设计模式.模版方法模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 16:57
 */
class ConcreteClass_Caixin extends AbstractClass {
    @Override
    protected void pourVegetable() {
        System.out.println("下锅的蔬菜是菜心");
    }

    @Override
    protected void pourSauce() {
        System.out.println("下锅的酱料是辣椒");
    }
}
