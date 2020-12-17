package com.wind.androidlearn.bassis.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * @Author: LL
 * @Description:解决handler内存泄漏问题
 * @Date:Create：in 2020/12/15 15:56
 */
class HandlerActivity extends Activity {

    MyHandler myHandler;

    /**
     * 解决内存泄漏
     */
    private static class MyHandler extends Handler {
        private WeakReference<HandlerActivity> mWeakReference;

        public MyHandler(HandlerActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity handlerActivity = mWeakReference.get();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHandler = new MyHandler(this);
        donInBackground();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        Button button = new Button(this);
        button.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void donInBackground() {
        new Thread(() -> {
            Looper.prepare();
            Message msg = Message.obtain();
            msg.what = 1;
            msg.obj = "测试";
            myHandler.sendMessage(msg);
            Looper.loop();
        }).start();
    }
}
