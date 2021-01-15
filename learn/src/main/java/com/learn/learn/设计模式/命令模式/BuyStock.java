package com.learn.learn.设计模式.命令模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 10:32
 */
class BuyStock implements Order {

    private Stock abcStock;

    public BuyStock(Stock abcStock) {
        this.abcStock = abcStock;
    }

    @Override
    public void execute() {
        abcStock.buy();
    }
}
