package com.wind.androidlearn.设计模式.桥接模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 15:59
 */
abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }
    public abstract void draw();
}
