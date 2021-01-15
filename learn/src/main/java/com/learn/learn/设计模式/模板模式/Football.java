package com.learn.learn.设计模式.模板模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 16:21
 */
class Football extends Game {

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }
}

