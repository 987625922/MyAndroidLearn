package com.wind.androidlearn.设计模式.模板模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 16:19
 */
public abstract class Game {
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();
    public final void play(){
        initialize();
        startPlay();
        endPlay();
    }
}
