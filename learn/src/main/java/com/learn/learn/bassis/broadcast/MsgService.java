package com.learn.learn.bassis.broadcast;
/**
 * Created by Administrator on 2018/8/25 0025.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 类 名： MsgService<br>
 * 说 明：<br>
 * 创建日期：2018/8/25 0025<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * 待做事情：
 */
public class MsgService extends Service {
    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    private Intent intent = new Intent("com.wyt.communication.RECEIVER");


    /**
     * 模拟下载任务，每秒钟更新一次
     */
    public void startDownLoad(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(progress < MAX_PROGRESS){
                    progress += 5;
                    Log.i("service---->",progress+"");
                    //发送Action为com.example.communication.RECEIVER的广播
                    intent.putExtra("progress", progress);
                    sendBroadcast(intent);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        progress = 0;
        startDownLoad();
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
