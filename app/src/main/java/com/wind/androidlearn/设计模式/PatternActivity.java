package com.wind.androidlearn.设计模式;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wind.androidlearn.设计模式.Builder.Builder;
import com.wind.androidlearn.设计模式.factory.Cousins;
import com.wind.androidlearn.设计模式.factory.Drink;
import com.wyt.zdf.myapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


//设计模式的使用
public class PatternActivity extends AppCompatActivity {

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    private static Intent newIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PatternActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_builder, R.id.btn_factory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_builder:
                Builder builder = new Builder.Build().writeIsBuild().writeIsLong().getBuilder();
                Log.d("->", builder.toString());
                break;
            case R.id.btn_factory:
                Drink drink = Cousins.create("可乐");
                drink.make();
                break;
        }
    }
}
