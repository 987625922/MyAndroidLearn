package com.learn.learn.设计模式.状态模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/17 15:16
 */
class StartState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("Play is in start state");
        context.setState(this);
    }
}
