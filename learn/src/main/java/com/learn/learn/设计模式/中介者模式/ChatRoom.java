package com.learn.learn.设计模式.中介者模式;

import java.util.Date;

/**
 * @Author: LL
 * @Description: 中介者类
 * @Date:Create：in 2020/12/22 15:30
 */
class ChatRoom {
    public static void showMessage(User user, String message) {
        System.out.println(new Date().toString() + " [" + user.getName() + "] : " + message);
    }
}
