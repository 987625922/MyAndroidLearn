package com.learn.learn.设计模式.命令模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/21 10:28
 */
class Stock {
    private String name = "ABC";
    private int quantity = 10;
    public void buy(){
        System.out.println("Stock [ Name:"+name+",Quantity:"+quantity+"] bought");
    }
    public void sell(){
        System.out.println("Stock [ Name:"+name+",Quantity:"+quantity+"] sold");
    }
}
