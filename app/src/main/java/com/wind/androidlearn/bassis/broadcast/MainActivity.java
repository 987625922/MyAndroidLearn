package com.wind.androidlearn.bassis.broadcast;
/**
 * Created by Administrator on 2018/8/25 0025.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
 * 待做事情：
 */
public class MainActivity  extends Activity {
    private ProgressBar mProgressBar;
    private Intent mIntent;
    private MsgReceiver msgReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wyt.communication.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //启动服务
                mIntent = new Intent(MainActivity.this,MsgService.class);
                startService(mIntent);
            }
        });

    }


    @Override
    protected void onDestroy() {
        //停止服务
        stopService(mIntent);
        //注销广播
        unregisterReceiver(msgReceiver);
        super.onDestroy();
    }


    /**
     * 广播接收器
     * @author len
     *
     */
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            int progress = intent.getIntExtra("progress", 0);
            Log.i("---->",progress+"");
            mProgressBar.setProgress(progress);
        }

    }

}

