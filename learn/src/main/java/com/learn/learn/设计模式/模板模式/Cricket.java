package com.learn.learn.设计模式.模板模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 16:20
 */
class Cricket extends Game {
    @Override
    void endPlay() {
        System.out.println("Cricket Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }
}
