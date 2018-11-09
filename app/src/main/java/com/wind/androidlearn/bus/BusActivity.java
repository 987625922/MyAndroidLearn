package com.wind.androidlearn.bus;

import android.widget.Toast;

import com.wind.androidlearn.BaseActivity;
import com.wyt.zdf.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BusActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_bus;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        //注册eventbus
        EventBus.getDefault().register(this);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消eventbus
        EventBus.getDefault().unregister(this);
    }

    /**
     * eventbus 监听
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        Toast.makeText(BusActivity.this, event, Toast.LENGTH_SHORT).show();
    }
}
