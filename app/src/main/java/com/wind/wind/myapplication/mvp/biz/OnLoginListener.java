package com.wind.wind.myapplication.mvp.biz;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import com.wind.wind.myapplication.mvp.bean.User;

/**
 * 类 名： OnLoginListener<br>
 * 说 明：<br>
 * 创建日期：2018/8/21 0021<br>
 * 作 者：Devin Zhong<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：TODO<br>
 */
public interface OnLoginListener {

    void loginSuccess(User user);

    void loginFailed();
}
