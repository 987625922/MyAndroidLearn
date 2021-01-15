package com.learn.learn.设计模式.中介者模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/22 15:31
 */
class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public void sendMessage(String message){
        ChatRoom.showMessage(this,message);
    }
}
