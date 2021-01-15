package com.learn.learn.bassis.datasave;

import android.content.Context;
import android.content.SharedPreferences;

import com.wyt.common.base.BaseActivity;

public class SharedPreferencesActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void start() {
        //存数据
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        sp.edit().putString("name", "小张").putInt("age", 11).commit();
        //取数据
        SharedPreferences sp1 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp1.getString("name", null);
        int age = sp1.getInt("age", 0);
    }

    @Override
    public void initData() {

    }
}
