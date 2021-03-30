package com.learn.learn.eventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.learn.learn.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/3/11 13:47
 */
public class EventBusActivity extends AppCompatActivity {

    private static Intent newIntent(Context context) {
        return new Intent(context, EventBusActivity.class);
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post("123");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true, priority = 100)
    public void onMessage(String message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
