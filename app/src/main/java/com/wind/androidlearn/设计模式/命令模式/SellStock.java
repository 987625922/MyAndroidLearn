package com.wind.androidlearn.设计模式.命令模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 10:39
 */
class SellStock implements Order{
    private Stock abcStock;
    public SellStock(Stock abcStock){
        this.abcStock = abcStock;
    }
    @Override
    public void execute(){
        abcStock.sell();
    }
}
