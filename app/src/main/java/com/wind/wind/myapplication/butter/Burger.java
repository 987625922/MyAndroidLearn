package com.wind.wind.myapplication.butter;
/**
 * Created by Administrator on 2018/8/22 0022.
 */

/**
 * 类 名： Burger<br>
 * 说 明：<br>
 * 创建日期：2018/8/22 0022<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public abstract class Burger implements Item {

    @Override
    public Picking picking() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
