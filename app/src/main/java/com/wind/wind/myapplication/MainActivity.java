package com.wind.wind.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wind.wind.myapplication.bassis.RecyclerImage.RecycleActivity;
import com.wind.wind.myapplication.bassis.provider.ProviderActivity;
import com.wyt.zdf.myapplication.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //recyclerview页面
        findViewById(R.id.btn_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RecycleActivity.class);
                startActivity(intent);
            }
        });
//        BroadcastReceiver广播实现跨进程数据交互
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, com.wind.wind.myapplication.bassis.broadcast.MainActivity.class);
                startActivity(intent);
            }
        });
//        binder跨进程数据交互
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, com.wind.wind.myapplication.bassis.broadcast.binder.MainActivity.class);
                startActivity(intent);
            }
        });
//        content provider实现
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProviderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存销毁之前的数据

        Log.d("", "onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("", "onRestoreInstanceState");
    }



}
