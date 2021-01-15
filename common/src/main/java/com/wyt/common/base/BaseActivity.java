package com.wyt.common.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wyt.common.utils.WindowUtil;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/15 16:03
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final Context context = this;
    protected final String TAG = getClass().getName();

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        WindowUtil.setHideVirtualKey(getWindow());
        initView();
        initData();
        start();
    }

    /**
     * 初始化 View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 开始请求
     */
    protected abstract void start();
}
