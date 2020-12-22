package com.wind.androidlearn.设计模式.外观模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 15:43
 */
class ShapeMaker {
    private Shape circle;
    private Shape rectangle;
    private Shape square;

    public ShapeMaker() {
        circle = new Circle();
        rectangle = new Rectangle();
        square = new Square();
    }

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectangle.draw();
    }

    public void drawSquare() {
        square.draw();
    }
}
