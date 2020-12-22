package com.wind.androidlearn.设计模式.模板模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 16:25
 */
class TemplatePatternDemo {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
