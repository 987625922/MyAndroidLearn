package com.wind.androidlearn.ijkplayer;

import android.content.Context;
import android.content.Intent;

import com.wind.androidlearn.BaseActivity;
import com.wind.androidlearn.ijkplayer.media.bilibili.VideoView;
import com.wyt.zdf.myapplication.R;

import static com.wind.androidlearn.ijkplayer.media.bilibili.VideoView.SCALETYPE_FILLPARENT;

public class IjkplayActivity extends BaseActivity {
    VideoView mVideoPlayer;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, IjkplayActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }
    @Override
    protected void findView() {
        mVideoPlayer = findViewById(R.id.mVideoPlayer);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        //把下面3行代码写了就可以播放视频了
        mVideoPlayer.setScreenScaleType(SCALETYPE_FILLPARENT);
        mVideoPlayer.play("http://yunstudy.koo6.cn/2018Videofile/ACC46413337d732f4acbe8c8458adb35a0f/rjbpb3njywsc02/1000kb.m3u8");
        mVideoPlayer.setOnCallBack(this, new VideoView.onCallBack() {

            @Override
            public void startPlay() {

            }

            @Override
            public void pause() {

            }

            @Override
            public void play() {

            }

            @Override
            public void error(String error) {

            }

            @Override
            public void playEnd() {

            }

            @Override
            public void stop() {

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ijkplay;
    }
}
