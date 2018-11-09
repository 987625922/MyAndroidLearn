package com.wind.androidlearn.bassis.ThradHandle;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.wind.androidlearn.BaseActivity;

public class ThreadHandleActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        new ThreadB().start();
    }

    @SuppressLint("HandlerLeak")
    Handler threadHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //收到来自于ThreadB的消息，注意这里运行在ThreadA线程中

            //......
        }
    };


    class ThreadB extends Thread{

        @Override
        public void run() {
            super.run();


            Message message = new Message();
            message.obj = "ThreadB发送消息到ThreadA";
            //......
            threadHandler.sendMessage(message);


        }
    }

}
