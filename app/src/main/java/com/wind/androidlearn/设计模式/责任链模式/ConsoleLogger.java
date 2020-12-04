package com.wind.androidlearn.设计模式.责任链模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/4 17:55
 */
class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Console::Logger: " + message);
    }
}
