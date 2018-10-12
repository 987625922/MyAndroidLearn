package com.wind.wind.myapplication.ijkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wind.wind.myapplication.BaseActivity;
import com.wind.wind.myapplication.ijkplayer.media.bilibili.IRenderView;
import com.wind.wind.myapplication.ijkplayer.media.bilibili.IjkVideoView;
import com.wyt.zdf.myapplication.R;

import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoPlayerActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout mFlVideoPlayer;
    private IjkVideoView mVideoPlayer;
    //控制面板
    private RelativeLayout mVideoPlayerControlFrame;
    private ImageView mVideoPlayerBack;
    private TextView mVideoPlayerTitle;
    private ImageView mVideoPlayerDirection;
    private TextView mVideoPlayerCurrentTime;
    private TextView mVideoPlayerTotalTime;
    private SeekBar mVideoPlayerSeekBar;
    private ProgressBar mApp_video_loading;
    private ImageView mVideoPlayerPauseResume;

    private int mWidthPixels;
    //手势
    private GestureDetector mDetector;
    // 调节亮度
    private AudioManager mAudioManager;
    // 调节声音
    private int mMaxVolume;
    //bug不知道为什么ijk需要设置这个控件
    private TableLayout tableLayout;
    //控件设置相关
    private QueryBean mQueryBean;

    private String url = "http://flv2.bn.netease.com/videolib3/1611/28/nNTov5571" +
            "/SD/nNTov5571-mobile.mp4";
    private String mVideoUrl;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void findView() {
        //屏幕常亮
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //声音
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mQueryBean = new QueryBean(this);

        mWidthPixels = getResources().getDisplayMetrics().widthPixels;

        mFlVideoPlayer = findViewById(R.id.flVideoPlayer);
        mVideoPlayer = findViewById(R.id.mVideoPlayer);
        mVideoPlayerControlFrame = findViewById(R.id.mVideoPlayerControlFrame);
        mVideoPlayerBack = findViewById(R.id.mVideoPlayerBack);
        mVideoPlayerTitle = findViewById(R.id.mVideoPlayerTitle);
        mVideoPlayerDirection = findViewById(R.id.mVideoPlayerDirection);

        mVideoPlayerCurrentTime = findViewById(R.id.mVideoPlayerCurrentTime);
        mVideoPlayerTotalTime = findViewById(R.id.mVideoPlayerTotalTime);
        mVideoPlayerSeekBar = findViewById(R.id.mVideoPlayerSeekBar);
        mApp_video_loading = findViewById(R.id.app_video_loading);
        mApp_video_loading.setVisibility(View.VISIBLE);
        mVideoPlayerPauseResume = findViewById(R.id.mVideoPlayerPauseResume);


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListener() {

        mVideoPlayerSeekBar.setMax(1000);
        mVideoPlayerSeekBar.setOnSeekBarChangeListener(mSeekListener);

        mVideoPlayerBack.setOnClickListener(this);
        mVideoPlayerControlFrame.setOnClickListener(this);
        mVideoPlayerDirection.setOnClickListener(this);
        mVideoPlayerPauseResume.setOnClickListener(this);

        tableLayout = findViewById(R.id.tab);
        //手势
        mDetector = new GestureDetector(this, new PlayerGestureListener());

        mFlVideoPlayer.setClickable(true);
        mFlVideoPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mDetector.onTouchEvent(motionEvent))
                    return true;

                // 处理手势结束
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        endGesture();
                        break;
                }

                return false;
            }
        });


    }


    @Override
    protected void initData() {

        mVideoUrl = url;

        netMonitorHandler = new HyperHandler();
        // 5秒后控制面板消失
        mControlFrameTimer = new ControlFrameTimer(5000, 5000);
        mVideoPlayerTitle.setText("播放器");

        initVideoView();
    }

    /**
     * 初始化视频播放器
     */
    private void initVideoView() {
//        mVideoPlayer.setDecodingType(IjkVideoView.SOFTWARE_DECODING);
        //视频播放监听
        mVideoPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                if (null != mApp_video_loading &&
                        mApp_video_loading.getVisibility() == View.VISIBLE) {
                    mApp_video_loading.setVisibility(View.GONE);
                }
                if (null != netMonitorHandler) {
                    netMonitorHandler.sendEmptyMessage(MSG_RESET_RELOAD_COOL_DOWN);
                }
            }
        });
        //视频完成监听
        mVideoPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
//                if (!StrUtils.isNetWorkConnected(VideoPlayerActivity.this)) {
//                    //网络不可用
//                    if (null != netMonitorHandler) {
//                        netMonitorHandler.sendEmptyMessage(MSG_SHOW_NO_NETWORK);
//                    }
//                } else
                if (getPlayPercentage() >= 99) {
//                    showCompletionDialog();
                    Toast.makeText(VideoPlayerActivity.this, "视频播放完成", Toast.LENGTH_LONG).show();
                }

            }
        });
        //视频播放错误监听
        mVideoPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int framework_err, int extra) {
                String error = "";
                switch (framework_err) {
                    case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
//                    case IjkVideoView.INVALID_VIDEO_SOURCE://视频无法解析
                    case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                        error = "视频地址无法解析哦";
                        break;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    case MediaPlayer.MEDIA_ERROR_IO:
                        error = "与服务器断开了链接";
                        break;
                    case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                        error = "连接超时";
                        break;
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        error = "抱歉,播放器遇到了未知错误";
                        break;
                }
                if (null != netMonitorHandler) {
                    netMonitorHandler.obtainMessage(MSG_SHOW_ERROR_DIALOG, error).sendToTarget();
                }
//                if (null != mApp_video_loading && mApp_video_loading.getVisibility() == View.VISIBLE) {
//                    mApp_video_loading.setVisibility(View.GONE);
//                }
                // 返回true 不执行OnCompletion()
                return true;
            }
        });
        mVideoPlayer.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        mVideoPlayer.setVideoPath(mVideoUrl);

        mVideoPlayer.setHudView(tableLayout);
    }


    @Override
    protected void onResume() {
        super.onResume();
        play(false);
    }

    @Override
    protected void onPause() {
        pause();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mVideoPlayerBack:
                finish();
                break;
            //显示或隐藏控制面板
            case R.id.mVideoPlayerControlFrame:
                controlFrameOnClick();
                break;
            //屏幕方向切换
            case R.id.mVideoPlayerDirection:
                directionSwitch();
                break;
            case R.id.mVideoPlayerPauseResume:
                if (null != mVideoPlayer) {
                    if (mVideoPlayer.isPlaying()) {
                        pause();
                    } else {
                        play(false);
                    }
                }
                break;
            case R.id.mConnect_text:
                //点击重新加载
//                if (StrUtils.isNetWorkConnected(VideoPlayerActivity.this)) {
//                    if (null != netMonitorHandler) {
//                        netMonitorHandler.sendEmptyMessage(MSG_HIDEN_NO_NETWORK);
//                    }
//                    reload();
//                }
                break;
        }
    }

    /**
     * 设置播放器url 开始播放
     */
    private void play(boolean needSeek) {
        if (null != mVideoPlayer && !mVideoPlayer.isPlaying()) {
            mVideoPlayer.start();
            if (mLastPosition >= 0 && needSeek) {
                mVideoPlayer.seekTo(mLastPosition);
            }
        }
        if (null != mVideoPlayerPauseResume) {
            mVideoPlayerPauseResume.setImageDrawable(getResources().
                    getDrawable(R.mipmap.ic_white_player_pause));
        }
    }

    private int mLastPosition;

    /**
     * 播放器暂停
     */
    private void pause() {
        if (null != mVideoPlayer
                && mVideoPlayer.canPause()) {
            mVideoPlayer.pause();
            mLastPosition = mVideoPlayer.getCurrentPosition();
        }
        if (null != mVideoPlayerPauseResume) {
            mVideoPlayerPauseResume.setImageDrawable(getResources().
                    getDrawable(R.mipmap.ic_white_player_play));
        }
    }

    private int screenOrientation;

    /**
     * 屏幕方向改变回调
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        screenOrientation = newConfig.orientation;
    }

    /**
     * 屏幕方向切换
     */
    private void directionSwitch() {
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏切换成竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //竖屏切换成横屏SENSOR
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    /**
     * 获取当前播放百分比
     *
     * @return 百分比数字
     */
    private int getPlayPercentage() {
        long position = 0l;
        long duration = 0l;

        if (null != mVideoPlayer) {
            position = mVideoPlayer.getCurrentPosition();
            duration = mVideoPlayer.getDuration();
        }
        if (duration > 0) {
            return (int) (100L * position / duration);
        } else {
            return 0;
        }
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        netMonitorHandler.removeMessages(MESSAGE_HIDE_CENTER_BOX);
        netMonitorHandler.sendEmptyMessageDelayed(MESSAGE_HIDE_CENTER_BOX, 500);
    }

    /**
     * 手势回调
     */
    public class PlayerGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean mFirstTouch;
        private boolean mVolumeControl;

        /**
         * 双击
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mVideoPlayer.toggleAspectRatio();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mFirstTouch = true;
            return super.onDown(e);

        }

        /**
         * 滑动
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            float deltaY = mOldY - e2.getY();
            if (mFirstTouch) {
                mVolumeControl = mOldX > mWidthPixels * 0.5f;
                mFirstTouch = false;
            }
            float percent = deltaY / mVideoPlayer.getWidth();
            if (mVolumeControl) {
                onVolumeSlide(percent);
            } else {
                onBrightnessSlide(percent);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            controlFrameOnClick();
            return true;
        }
    }

    private ControlFrameTimer mControlFrameTimer;

    /**
     * 显示或隐藏控制面板
     */
    private void controlFrameOnClick() {
        if (null != mVideoPlayerControlFrame) {
            if (mVideoPlayerControlFrame.getVisibility() == View.VISIBLE) {
                mVideoPlayerControlFrame.setVisibility(View.GONE);
            } else {
                mVideoPlayerControlFrame.setVisibility(View.VISIBLE);

                if (null != netMonitorHandler) {
                    netMonitorHandler.sendEmptyMessage(MSG_SHOW_PROGRESS);
                }

                if (!mDragging && null != mControlFrameTimer) {
                    mControlFrameTimer.cancel();
                    mControlFrameTimer.start();
                }
            }
        }
    }

    /**
     * 控制面板消失倒计时
     */
    class ControlFrameTimer extends CountDownTimer {
        public ControlFrameTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (null != mVideoPlayerControlFrame) {
                mVideoPlayerControlFrame.setVisibility(View.GONE);
            }
        }
    }


    private int mVolume = -1;

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;
        }
        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        int i = (int) (index * 1.0 / mMaxVolume * 100);
        String s = i + "%";
        if (i == 0) {
            s = "off";
        }
        // 显示
        mQueryBean.id(R.id.app_video_volume_icon).image(i == 0 ? R.mipmap.ic_volume_off_white_36dp : R.mipmap.ic_volume_up_white_36dp);
        mQueryBean.id(R.id.app_video_brightness_box).gone();
        mQueryBean.id(R.id.app_video_volume_box).visible();
        mQueryBean.id(R.id.app_video_volume_box).visible();
        mQueryBean.id(R.id.app_video_volume).text(s).visible();
    }

    private float mBrightness = -1;

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f) {
                mBrightness = 0.50f;
            } else if (mBrightness < 0.01f) {
                mBrightness = 0.01f;
            }
        }
        Log.d(this.getClass().getSimpleName(), "mBrightness:" + mBrightness + ",percent:" + percent);
        mQueryBean.id(R.id.app_video_brightness_box).visible();
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        mQueryBean.id(R.id.app_video_brightness).text(((int) (lpa.screenBrightness * 100)) + "%");
        getWindow().setAttributes(lpa);
    }


    private long mDuration;
    private boolean mInstantSeeking = true;
    private HyperHandler netMonitorHandler;
    private Runnable mLastSeekBarRunnable;
    /**
     * 时间进度条
     */
    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser)
                return;
            final int newPosition = (int) (mDuration * progress) / 1000;
            String time = generateTime(newPosition);
            if (mInstantSeeking) {
                netMonitorHandler.removeCallbacks(mLastSeekBarRunnable);
                mLastSeekBarRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (null != mVideoPlayer) {
                            mVideoPlayer.seekTo(newPosition);
                        }
                    }
                };
                netMonitorHandler.postDelayed(mLastSeekBarRunnable, SEEK_TO_POST_DELAY_MILLIS);
            }
            mVideoPlayerCurrentTime.setText(time);
        }

        /**
         * 拖动开始
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mDragging = true;
            if (null != netMonitorHandler) {
                netMonitorHandler.removeMessages(MSG_SHOW_PROGRESS);
            }
        }

        /**
         * 拖动结束
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mDragging = false;

            if (!mInstantSeeking && null != mVideoPlayer && mDuration > 0) {
                mVideoPlayer.seekTo((int) (mDuration * seekBar.getProgress()) / 1000);
            }
            if (null != netMonitorHandler) {
                netMonitorHandler.removeMessages(MSG_SHOW_PROGRESS);
                netMonitorHandler.sendEmptyMessageDelayed(MSG_SHOW_PROGRESS, 1000);
            }
        }
    };

    private static final int MSG_SHOW_NO_WIFI_DIALOG = 0X0001;
    private static final int MSG_DISMISS_NO_WIFI_DIALOG = 0X0002;
    private static final int MSG_SHOW_PROGRESS = 0X0004;
    private static final int MSG_RESET_RELOAD_COOL_DOWN = 0X0005;
    private static final int MSG_SHOW_NO_NETWORK = 0X0006;
    private static final int MSG_HIDEN_NO_NETWORK = 0X0007;
    private static final int MSG_SHOW_ERROR_DIALOG = 0X0008;
    private static final int MESSAGE_HIDE_CENTER_BOX = 0X0009;
    private static final int SEEK_TO_POST_DELAY_MILLIS = 200;

    private boolean mDragging;
    private boolean reloadCoolDown = false;

    @SuppressLint("HandlerLeak")
    class HyperHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null == msg) {
                return;
            }
            switch (msg.what) {
                case MSG_SHOW_NO_WIFI_DIALOG:
//                    showNoWifiDialog();
                    break;
                case MESSAGE_HIDE_CENTER_BOX:
                    findViewById(R.id.app_video_volume_box).setVisibility(View.GONE);
                    findViewById(R.id.app_video_brightness_box).setVisibility(View.GONE);
                    findViewById(R.id.app_video_fastForward_box).setVisibility(View.GONE);

                    break;
                case MSG_DISMISS_NO_WIFI_DIALOG:
//                    dismissNoWifiDialog();
                    break;
                case MSG_SHOW_PROGRESS:
                    if (!mDragging && null != mVideoPlayerControlFrame
                            && mVideoPlayerControlFrame.getVisibility() == View.VISIBLE) {
                        msg = obtainMessage(MSG_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (setVideoProgress() % 1000));
                    }
                    break;
                case MSG_RESET_RELOAD_COOL_DOWN:
                    reloadCoolDown = false;
                    break;
                case MSG_SHOW_NO_NETWORK:
//                    if (null != mConnectText) {
//                        mConnectText.setVisibility(View.VISIBLE);
//                        if (null != mApp_video_loading &&
//                                mApp_video_loading.getVisibility() == View.VISIBLE) {
//                            mApp_video_loading.setVisibility(View.GONE);
//                        }
//                    }
                    break;
                case MSG_HIDEN_NO_NETWORK:
//                    if (null != mConnectText) {
//                        mConnectText.setVisibility(View.GONE);
//                    }
                    break;
                case MSG_SHOW_ERROR_DIALOG:
//                    if (null != msg.obj
//                            && !TextUtils.isEmpty(msg.obj.toString())) {
//                        showErrorDialog(msg.obj.toString());
//                    }
                    break;
            }
        }
    }

    private String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.CHINA, "%02d:%02d:%02d", hours, minutes,
                    seconds);
        } else {
            return String.format(Locale.CHINA, "%02d:%02d", minutes, seconds);
        }
    }

    private long setVideoProgress() {
        if (mVideoPlayer == null || mDragging)
            return 0;
        long position = mVideoPlayer.getCurrentPosition();
        long duration = mVideoPlayer.getDuration();
        long pos = 0;
        if (mVideoPlayerSeekBar != null) {
            if (duration > 0) {
                pos = 1000L * position / duration;
                mVideoPlayerSeekBar.setProgress((int) pos);
            }
            int percent = mVideoPlayer.getBufferPercentage() * 10;
            if (pos >= percent) {
                mVideoPlayerSeekBar.setSecondaryProgress((int) pos + 25);
            } else {
                mVideoPlayerSeekBar.setSecondaryProgress(percent);
            }
        }
        mDuration = duration;
        if (null != mVideoPlayerTotalTime) {
            mVideoPlayerTotalTime.setText(generateTime(mDuration));
        }
        if (null != mVideoPlayerCurrentTime) {
            mVideoPlayerCurrentTime.setText(generateTime(position));
        }
        return position;
    }
    /**
     * 重新加载
     * boolean true 表示reload 成功 false 表示失败
     */
    private boolean reload() {
        if (reloadCoolDown) {
            Toast.makeText(this, "正在加载中，请稍后…", Toast.LENGTH_SHORT).show();
            return false;
        }
        reloadCoolDown = true;
        if (null != mApp_video_loading) {
            mApp_video_loading.setVisibility(View.VISIBLE);
        }
        if (null != mVideoPlayer) {
            mLastPosition = mVideoPlayer.getCurrentPosition();
            mVideoPlayer.setVideoPath(mVideoUrl);
        }

        play(true);

        if (null != netMonitorHandler) {
            netMonitorHandler.sendEmptyMessageDelayed(MSG_RESET_RELOAD_COOL_DOWN, 8000);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        if (null != netMonitorHandler) {
            netMonitorHandler.removeCallbacksAndMessages(null);
            netMonitorHandler = null;
        }
        if (null != mVideoPlayer) {
            mVideoPlayer.stopPlayback();
            mVideoPlayer = null;
        }
//        mLastPosition = 0;
//        isNoWifePlay = false;
        super.onDestroy();
    }
}
