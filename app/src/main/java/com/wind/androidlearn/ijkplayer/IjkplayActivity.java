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
        mVideoPlayer.play("http://flv2.bn.netease.com/videolib3/1611/28/nNTov5571/SD/nNTov5571-mobile.mp4");
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