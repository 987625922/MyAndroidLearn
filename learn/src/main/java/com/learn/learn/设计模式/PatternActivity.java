package com.learn.learn.设计模式;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.learn.R;
import com.learn.learn.设计模式.Builder.Builder;
import com.learn.learn.设计模式.factory.Cousins;
import com.learn.learn.设计模式.factory.Drink;


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
        findViewById(R.id.btn_builder).setOnClickListener(v -> {
            Builder builder = new Builder.Build().writeIsBuild().writeIsLong().getBuilder();
            Log.d("->", builder.toString());
        });
        findViewById(R.id.btn_factory).setOnClickListener(v -> {
            Drink drink = Cousins.create("可乐");
            drink.make();
        });
    }
}
