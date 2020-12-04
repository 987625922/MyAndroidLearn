package com.wind.androidlearn.设计模式.责任链模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/4 17:56
 */
class FileLogger extends AbstractLogger {

    public FileLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("File::Logger: " + message);
    }
}