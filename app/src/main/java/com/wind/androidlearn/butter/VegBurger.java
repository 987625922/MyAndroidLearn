package com.wind.androidlearn.butter;
/**
 * Created by Administrator on 2018/8/22 0022.
 */

/**
 * 类 名： VegBurger<br>
 * 说 明：<br>
 * 创建日期：2018/8/22 0022<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class VegBurger extends Burger {

    @Override
    public float price() {
        return 25.0f;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }
}
