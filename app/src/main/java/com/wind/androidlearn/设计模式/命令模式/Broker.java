package com.wind.androidlearn.设计模式.命令模式;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 10:34
 */
class Broker {
    private List<Order> orderList = new ArrayList<Order>();

    public void takeOrder(Order order) {
        orderList.add(order);
    }

    public void placeOrders() {
        for (Order order : orderList) {
            order.execute();
        }
        orderList.clear();
    }
}
