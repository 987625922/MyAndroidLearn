package com.learn.learn.插件化宿主首页;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.learn.learn.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 插件化的首页
 */
public class MainActivity extends AppCompatActivity {

    private static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);

        findViewById(R.id.mBtnLoadPlugin)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadPlugin();
                    }
                });


        findViewById(R.id.mBtnStartProxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProxy();
            }


        });
    }

    /**
     * 加载插件
     */
    private void loadPlugin() {
        HookManager.getInstance().loadPlugin(this);
        Toast.makeText(this, "加载完成", Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转插件
     */
    private void startProxy() {
        Intent intent = new Intent(this, ProxyActivity.class);//这里就是一个占坑的activity
        //这里是拿到我们加载的插件的第一个activity的全类名
        intent.putExtra("ClassName", HookManager.getInstance().packageInfo.activities[0].name);

        startActivity(intent);
    }
}