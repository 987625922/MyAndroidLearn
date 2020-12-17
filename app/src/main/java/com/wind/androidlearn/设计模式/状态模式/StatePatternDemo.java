package com.wind.androidlearn.设计模式.状态模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/17 15:17
 */
class StatePatternDemo {
    public static void main(String[] args) {
        Context context = new Context();

        StartState startState = new StartState();
        startState.doAction(context);

        System.out.println(context.getState().toString());

        StopState stopState = new StopState();
        stopState.doAction(context);

        System.out.println(context.getState().toString());
    }
}
