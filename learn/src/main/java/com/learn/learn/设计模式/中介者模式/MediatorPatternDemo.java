package com.learn.learn.设计模式.中介者模式;

/**
 * @Author: LL
 * @Description: 中介者模式的使用
 * @Date:Create：in 2020/12/22 15:33
 */
class MediatorPatternDemo {

    public static void main(String[] args) {
       User robert = new User("Robert");
       User john = new User("john");

       robert.sendMessage("Hi! John!");
       john.sendMessage("Hello! Robert!");
    }
}
