package com.learn.learn.设计模式.状态模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/17 15:14
 */
class Context {
    private State state;

    public Context() {
        state = null;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
