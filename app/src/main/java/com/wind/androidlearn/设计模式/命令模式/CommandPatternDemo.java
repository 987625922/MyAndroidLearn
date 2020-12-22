package com.wind.androidlearn.设计模式.命令模式;

/**
 * @Author: LL
 * @Description: 通过一个list依次执行类中的方法
 * @Date:Create：in 2020/12/21 10:38
 */
class CommandPatternDemo {
    public static void main(String[] args) {
        Stock abcStock = new Stock();
        BuyStock buyStockOrder = new BuyStock(abcStock);
        SellStock sellStockOrder = new SellStock(abcStock);
        Broker broker = new Broker();
        broker.takeOrder(buyStockOrder);
        broker.takeOrder(sellStockOrder);
        broker.placeOrders();
    }
}
