package com.wind.wind.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.wind.wind.myapplication.screenmatch.Density;

public abstract class BaseActivity extends Activity {
    protected Activity mActivity;

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

//        Display display = getWindowManager().getDefaultDisplay();
//        int screenWidth = display.getWidth();
//        int screenHeight = display.getHeight();
//        LogUtil.e("---", "高：" + screenHeight + "；宽：" + screenWidth + "\n"
//                + "dimen xx1：" + getResources().getDimension(R.dimen.xx1) + "\n"
//                + "dimen yy1：" + getResources().getDimension(R.dimen.yy1) + "\n"
//                + "dimen xx1080：" + getResources().getDimension(R.dimen.xx1080) + "\n"
//                + "dimen yy1920：" + getResources().getDimension(R.dimen.yy1920) + "\n"
//                + PhoneUtils.getOnlyNumber(this));
//        ((CrashApplication)getApplication()).addActivity_(this);
        setOrientation();
        setContentView(getLayout());
        findView();
        setListener();
        initData();
    }

    /**
     * 初始化View
     */
    protected abstract void findView();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * toast
     *
     * @param toast toast内容
     */
    public void showToast(String toast) {
        if (toast != null && !"null".equals(toast) && !"".equals(toast)) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 今日头条适配
     */
    public void setOrientation() {
        Density.setDefault(mActivity);
    }
}
