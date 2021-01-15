package com.learn.learn.设计模式.责任链模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/4 17:55
 */
class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Error Console::Logger: " + message);
    }
}