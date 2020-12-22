package com.wind.androidlearn.设计模式.模版方法模式;

/**
 * @Author: LL
 * @Description: 一次性实现一个算法的不变的部分，并将可变的行为留给子类来实现；
 * @Date:Create：in 2020/12/22 17:03
 */
class Template {
    public static void main(String[] args) {
        ConcreteClass_Baocai baocai = new ConcreteClass_Baocai();
        baocai.cookProcess();

        ConcreteClass_Caixin caixin = new ConcreteClass_Caixin();
        caixin.cookProcess();
    }
}
