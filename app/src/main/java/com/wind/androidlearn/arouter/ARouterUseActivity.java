package com.wind.androidlearn.arouter;

import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wind.androidlearn.base.BaseActivity;
import com.wyt.zdf.myapplication.R;

@Route(path = "/test/activity")
public class ARouterUseActivity extends BaseActivity {

    @Autowired
    public String name;

    @Override
    protected int getLayout() {
        return R.layout.item_recycler;
    }

    @Override
    protected void findView() {
        ARouter.getInstance().inject(this);
        Log.e("ARouter参数",name);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }
}
