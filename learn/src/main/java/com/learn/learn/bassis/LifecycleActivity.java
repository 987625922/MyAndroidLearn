package com.learn.learn.bassis;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.learn.learn.R;

public class LifecycleActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        Log.e("LifecycleActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("LifecycleActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("LifecycleActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LifecycleActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LifecycleActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LifecycleActivity", "onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("LifecycleActivity", "onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("LifecycleActivity", "onActivityResult");
    }
}
