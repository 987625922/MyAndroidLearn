package com.wind.androidlearn.apt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @Author: LL
 * @Description: 使用了APT注解的类
 * @Date:Create：in 2020/12/31 16:57
 */
@Route("testMainActivity")
class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
