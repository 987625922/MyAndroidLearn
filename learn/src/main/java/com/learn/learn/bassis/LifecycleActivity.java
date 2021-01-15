package com.learn.learn.bassis;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.learn.learn.R;
import com.wyt.common.Utils.LogUtil;

public class LifecycleActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        LogUtil.e("LifecycleActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("LifecycleActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("LifecycleActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("LifecycleActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("LifecycleActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("LifecycleActivity", "onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.e("LifecycleActivity", "onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("LifecycleActivity", "onActivityResult");
    }
}
