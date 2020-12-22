package com.wind.androidlearn.设计模式.外观模式;

/**
 * @Author: LL
 * @Description:外观模式（Facade Pattern）隐藏系统的复杂性，
 * 并向客户端提供了一个客户端可以访问系统的接口。
 * 这种类型的设计模式属于结构型模式，它向现有的系统添加一个接口，来隐藏系统的复杂性。
 * @Date:Create：in 2020/12/22 15:46
 */
class FacadePatternDemo {
    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
