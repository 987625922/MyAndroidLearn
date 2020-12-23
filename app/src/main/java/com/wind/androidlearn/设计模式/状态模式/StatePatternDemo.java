package com.wind.androidlearn.设计模式.状态模式;

/**
 * @Author: LL
 * @Description: 类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。
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
