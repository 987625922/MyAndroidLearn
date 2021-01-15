package com.learn.learn.设计模式.桥接模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 16:05
 */
class Circle extends Shape {
    private int x, y, radius;

    protected Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(radius, x, y);
    }
}
