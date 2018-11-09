package com.wind.androidlearn.bassis.single;
/**
 * Created by Administrator on 2018/8/25 0025.
 */

/**
 * 类 名： SingleHander<br>
 * 说 明：<br> 单例模式
 * 创建日期：2018/8/25 0025<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class SingleHander {
    private SingleHander(){}
    private static class Single{
        private static SingleHander singleHander = new SingleHander();
    }
    public static SingleHander getSingle(){
        return Single.singleHander;
    }
}