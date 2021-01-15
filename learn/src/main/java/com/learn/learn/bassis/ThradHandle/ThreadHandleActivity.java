package com.learn.learn.bassis.ThradHandle;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.wyt.common.base.BaseActivity;

public class ThreadHandleActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new ThreadB().start();
    }

    @Override
    protected void start() {

    }

    @SuppressLint("HandlerLeak")
    Handler threadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //收到来自于ThreadB的消息，注意这里运行在ThreadA线程中
        }
    };


    class ThreadB extends Thread{
        @Override
        public void run() {
            super.run();
            Message message = new Message();
            message.obj = "ThreadB发送消息到ThreadA";
            threadHandler.sendMessage(message);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            });
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
