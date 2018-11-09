package com.wind.androidlearn.bassis.datasave;

import android.content.Context;
import android.content.SharedPreferences;

import com.wind.androidlearn.BaseActivity;

public class SharedPreferencesActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        //存数据
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        sp.edit().putString("name", "小张").putInt("age", 11).commit();
        //取数据
        SharedPreferences sp1 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp1.getString("name", null);
        int age = sp1.getInt("age", 0);

    }
}
