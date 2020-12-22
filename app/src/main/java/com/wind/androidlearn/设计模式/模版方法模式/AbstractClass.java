package com.wind.androidlearn.设计模式.模版方法模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 16:51
 */
abstract class AbstractClass {
    //模板方法,用来控制炒菜的流程
    final void cookProcess() {
        this.pourOil();
        this.heatOil();
        pourVegetable();
        pourSauce();
        this.fry();
    }

    void pourOil() {
        System.out.println("倒油");
    }

    void heatOil() {
        System.out.println("热油");
    }

    protected abstract void pourVegetable();

    protected abstract void pourSauce();

    void fry() {
        System.out.println("炒到熟");
    }
}
