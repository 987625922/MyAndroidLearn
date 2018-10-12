package com.wind.wind.myapplication.bassis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wind.wind.myapplication.bassis.Utils.LogUtil;
import com.wyt.zdf.myapplication.R;

public class 保存状态Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        if (savedInstanceState != null
                && savedInstanceState.getInt("currentposition") != 0) {
            //从savedInstanceState中获取onSaveInstanceState中保存的状态
//            videoView.seekTo(savedInstanceState.getInt("currentposition"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存videoview的状态
//        outState.putInt("currentposition", videoView.getCurrentPosition());
        LogUtil.w("tag", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
