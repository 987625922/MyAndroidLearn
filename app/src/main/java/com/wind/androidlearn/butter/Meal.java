package com.wind.wind.androidlearn.butter;
/**
 * Created by Administrator on 2018/8/22 0022.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名： Meal<br>
 * 说 明：<br>
 * 创建日期：2018/8/22 0022<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class Meal {
    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item){
        items.add(item);
    }

    public float getCost(){
        float cost = 0.0f;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems(){
        for (Item item : items) {
            System.out.print("Item : "+item.name());
            System.out.print(", Packing : "+item.picking().picking());
            System.out.println(", Price : "+item.price());
        }
    }
}