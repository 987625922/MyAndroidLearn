package com.wind.androidlearn.bassis;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class 横竖屏切换 extends Activity {
    private String TAG = "横竖屏切换";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: 保存数据");
        outState.putString("key", "value");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: 获取数据");
        String name = savedInstanceState.getString("key");
    }
}
