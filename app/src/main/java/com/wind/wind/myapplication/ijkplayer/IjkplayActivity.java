package com.wind.wind.myapplication.ijkplayer;

import android.net.Uri;

import com.wind.wind.myapplication.BaseActivity;
import com.wind.wind.myapplication.ijkplayer.media.bilibili.IRenderView;
import com.wind.wind.myapplication.ijkplayer.media.bilibili.IjkVideoView;
import com.wyt.zdf.myapplication.R;

public class IjkplayActivity extends BaseActivity {
    private IjkVideoView ijkVideoView;
    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/nNTov5571/SD/nNTov5571-mobile.mp4";

    @Override
    protected void findView() {
        ijkVideoView = findViewById(R.id.ijk_paly);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        ijkVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        ijkVideoView.setVideoURI(Uri.parse(VIDEO_URL));
        ijkVideoView.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ijkplay;
    }
}
