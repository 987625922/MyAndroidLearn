package com.wind.wind.myapplication.mvp.biz;
/**
 * Created by Administrator on 2018/8/21 0021.
 */

import com.wind.wind.myapplication.mvp.bean.User;

/**
 * 类 名： UserBiz<br>
 * 说 明：<br>
 * 创建日期：2018/8/21 0021<br>
 * 作 者：Devin Zhong<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：TODO<br>
 */
public class UserBiz implements IUserBiz
{

    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener)
    {
        //模拟子线程耗时操作
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //模拟登录成功
                if ("zhy".equals(username) && "123".equals(password))
                {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    loginListener.loginSuccess(user);
                } else
                {
                    loginListener.loginFailed();
                }
            }
        }.start();
    }
}
