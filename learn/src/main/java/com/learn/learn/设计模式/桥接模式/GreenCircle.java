package com.learn.learn.设计模式.桥接模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 15:59
 */
class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println(
                "Drawing Circle[ color: green,radius: " + radius
                        + ",x: " + x + ", " + y + "]"
        );
    }
}
