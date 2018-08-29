package com.wind.wind.myapplication.bassis.broadcast.binder;
/**
 * Created by Administrator on 2018/8/25 0025.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wyt.zdf.myapplication.R;

/**
 * 类 名： ProviderActivity<br>
 * 说 明：<br>
 * 创建日期：2018/8/25 0025<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class MainActivity  extends Activity {
    private MsgService msgService;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //绑定Service
        Intent intent = new Intent(MainActivity.this,MsgService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //开始下载
                msgService.startDownLoad();
            }
        });

    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            msgService = ((MsgService.MsgBinder)service).getService();

            //注册回调接口来接收下载进度的变化
            msgService.setOnProgressListener(new OnProgressListener() {

                @Override
                public void onProgress(int progress) {
                    mProgressBar.setProgress(progress);

                }
            });

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }


}
