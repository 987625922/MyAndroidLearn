package com.wind.androidlearn.eventbusandlivedatabus;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyliao.livedatabus.LiveDataBus;
import com.wind.androidlearn.base.BaseActivity;
import com.wyt.zdf.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class BusActivity extends BaseActivity {
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.eventbus)
    Button eventbus;
    @BindView(R.id.livedatabus)
    Button livedatabus;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BusActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }
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
        //livedatabus注册监听
        LiveDataBus.get()
                .with("key_name", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        content.setText(livedatabus.getText()+"livedatabus");
                        Toast.makeText(BusActivity.this, livedatabus.getText()+"livedatabus", Toast.LENGTH_SHORT).show();

                    }
                });

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
        content.setText(eventbus.getText() + "event");
        Toast.makeText(BusActivity.this, event, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.eventbus, R.id.livedatabus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eventbus:
                EventBus.getDefault().post(eventbus.getText() + "event");
                break;
            case R.id.livedatabus:
                LiveDataBus.get().with("key_name").postValue("livedatabus");
                break;
        }
    }
}
