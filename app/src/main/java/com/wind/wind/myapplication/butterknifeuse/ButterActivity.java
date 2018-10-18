package com.wind.wind.myapplication.butterknifeuse;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.wind.wind.myapplication.BaseActivity;
import com.wyt.zdf.myapplication.R;

import butterknife.BindView;

public class ButterActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ButterActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_butter;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        tvName.setText("222");
    }
}
