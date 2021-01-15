package com.learn.learn.设计模式.模版方法模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 16:56
 */
class ConcreteClass_Baocai extends AbstractClass {
    @Override
    protected void pourVegetable() {
        System.out.println("下锅的蔬菜是包菜");
    }

    @Override
    protected void pourSauce() {
        System.out.println("下锅的酱料是辣椒");
    }
}
