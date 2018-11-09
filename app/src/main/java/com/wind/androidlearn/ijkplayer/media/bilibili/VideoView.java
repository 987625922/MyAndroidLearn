package com.wind.wind.androidlearn.ijkplayer.media.bilibili;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.wyt.zdf.myapplication.R;

import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoView extends ViewGroup implements View.OnClickListener {

    private static final int MSG_SHOW_PROGRESS = 0X0004;
    private static final int MSG_SHOW_ERROR_DIALOG = 0X0008;
    private static final int MESSAGE_HIDE_CENTER_BOX = 0X0009;
    private static final int SEEK_TO_POST_DELAY_MILLIS = 200;
    private boolean mDragging;
    //最外层布局
    private ConstraintLayout mFlVideoPlayer;
    //上下文
    private Context context = null;
    //是否全屏
    private boolean isFull = false;
    //整个view布局
    private View mView;
    //WindowManager
    private WindowManager windowManager;
    //视频播放器
    private IjkVideoView ijkVideoView;
    //控制主布局
    private RelativeLayout mVideoPlayerControlFrame;
    //手势
    private GestureDetector mDetector;
    //获取屏幕宽，判断滑动的是左边还是右边，来判断调节音量还是亮度
    private int mWidthPixels;
    //进度条
    private SeekBar mVideoPlayerSeekBar;
    //视频总时间
    private TextView mVideoPlayerTotalTime;
    //视频播放的时间
    private TextView mVideoPlayerCurrentTime;
    //播放器标题
    private TextView mVideoPlayerTitle;
    //loading圈
    private ProgressBar mApp_video_loading;
    //返回
    private ImageView mVideoPlayerBack;
    //横竖屏切换
    private ImageView mVideoPlayerDirection;
    //暂停
    private ImageView mVideoPlayerPauseResume;
    // 声音控制器
    private AudioManager mAudioManager;
    //视频播放的位置
    private int mLastPosition;
    //音量
    private int mVolume = -1;
    //进度条
    private long mDuration;
    private boolean mInstantSeeking = true;
    private Runnable mLastSeekBarRunnable;
    //时间定时
    private ControlFrameTimer mControlFrameTimer;
    //存入外部activity
    private Activity activity = null;
    //竖屏时控件的高
    private int mViewHeight = 0;
    //视频的裁剪方式
    private String scaleType;
    /**
     * 可能会剪裁,保持原视频的大小，显示在中心,当原视频的大小超过view的大小超过部分裁剪处理
     */
    public static final String SCALETYPE_FITPARENT = "fitParent";
    /**
     * 可能会剪裁,等比例放大视频，直到填满View为止,超过View的部分作裁剪处理
     */
    public static final String SCALETYPE_FILLPARENT = "fillParent";
    /**
     * 将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中
     */
    public static final String SCALETYPE_WRAPCONTENT = "wrapContent";
    /**
     * 不剪裁,非等比例拉伸画面填满整个View
     */
    public static final String SCALETYPE_FITXY = "fitXY";
    /**
     * 不剪裁,非等比例拉伸画面到16:9,并完全显示在View中
     */
    public static final String SCALETYPE_16_9 = "16:9";
    /**
     * 不剪裁,非等比例拉伸画面到4:3,并完全显示在View中
     */
    public static final String SCALETYPE_4_3 = "4:3";

    private int mMaxVolume;
    private ImageView volumeImg;
    private TextView app_video_volume;
    private HyperHandler netMonitorHandler;
    private TextView app_video_brightness;
    private float mBrightness = -1;

    //bug不知道为什么ijk需要设置这个控件
    private TableLayout tableLayout;

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * view的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(l, t, r, b);
        }
    }

    /**
     * 测量view
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private int widthSpec;
    private int heightSpec;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int[] size;
        if (isFull) {
            //如果是全屏模式则获取Window的尺寸
            size = getWindowSize();
        } else {
            size = null;
        }
        //全屏模式下则获取父容器的尺寸 否则 为XML中设置的大小
        widthSpec = (isFull && size != null) ? MeasureSpec.makeMeasureSpec(size[0], MeasureSpec.EXACTLY) : widthMeasureSpec;
        heightSpec = (isFull && size != null) ? MeasureSpec.makeMeasureSpec(size[1], MeasureSpec.EXACTLY) : heightMeasureSpec;

        setMeasuredDimension(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec));
        measureChild(getChildAt(0), widthSpec, heightSpec);

        if (getChildAt(0) != null) {
            getChildAt(0).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 初始化播放器
     */
    private void initView() {
        mView = LayoutInflater.from(context).inflate(R.layout.activity_video_player,
                null, true);

        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mWidthPixels = getResources().getDisplayMetrics().widthPixels;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        ijkVideoView = mView.findViewById(R.id.mVideoPlayer);
        mVideoPlayerControlFrame = mView.findViewById(R.id.mVideoPlayerControlFrame);
        mVideoPlayerSeekBar = mView.findViewById(R.id.mVideoPlayerSeekBar);
        mVideoPlayerTotalTime = mView.findViewById(R.id.mVideoPlayerTotalTime);
        mVideoPlayerCurrentTime = mView.findViewById(R.id.mVideoPlayerCurrentTime);
        mVideoPlayerTitle = mView.findViewById(R.id.mVideoPlayerTitle);
        mApp_video_loading = mView.findViewById(R.id.app_video_loading);
        mVideoPlayerBack = mView.findViewById(R.id.mVideoPlayerBack);
        mVideoPlayerDirection = mView.findViewById(R.id.mVideoPlayerDirection);
        mVideoPlayerPauseResume = mView.findViewById(R.id.mVideoPlayerPauseResume);
        mFlVideoPlayer = mView.findViewById(R.id.fl_video_control);
        tableLayout = mView.findViewById(R.id.tab);
        volumeImg = mView.findViewById(R.id.app_video_volume_icon);
        app_video_volume = mView.findViewById(R.id.app_video_volume);
        app_video_brightness = mView.findViewById(R.id.app_video_brightness);
        addView(mView);

        initData();

        initVideoView();

    }

    /**
     * 初始化
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        mVideoPlayerSeekBar.setMax(1000);
        mVideoPlayerSeekBar.setOnSeekBarChangeListener(mSeekListener);
        mVideoPlayerBack.setOnClickListener(this);
        mVideoPlayerControlFrame.setOnClickListener(this);
        mVideoPlayerDirection.setOnClickListener(this);
        mVideoPlayerPauseResume.setOnClickListener(this);
        //手势
        mDetector = new GestureDetector(context, new PlayerGestureListener());
        //监听总布局的触控
        mFlVideoPlayer.setClickable(true);
        mFlVideoPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mDetector.onTouchEvent(motionEvent))
                    return true;

                // 处理手势结束
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    //用户抬起触发，关闭显示的图标
                    case MotionEvent.ACTION_UP:
                        endGesture();
                        break;
                }
                return false;
            }
        });
        netMonitorHandler = new HyperHandler();
        // 5秒后控制面板消失
        mControlFrameTimer = new ControlFrameTimer(5000, 5000);
        mVideoPlayerTitle.setText("播放器");
        ijkVideoView.setHudView(tableLayout);


    }

    /**
     * 初始化视频播放器
     */
    private void initVideoView() {

        //视频播放监听
        ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                if (null != mApp_video_loading &&
                        mApp_video_loading.getVisibility() == View.VISIBLE) {
                    mApp_video_loading.setVisibility(View.GONE);


                }

            }
        });
        //视频完成监听
        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                if (getPlayPercentage() >= 99) {
//                    Toast.makeText(context, "视频播放完成", Toast.LENGTH_LONG).show();
                    onCallBack.playEnd();
                }

            }
        });
        //视频播放错误监听
        ijkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
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
                // 返回true 不执行OnCompletion()
                return true;
            }
        });
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
     * 获取Window的尺寸
     *
     * @return 如果无法获取数据 , 则返回 NULL , 否则返回 Int[] -> [0]width  [1]height
     */
    private int[] getWindowSize() {
        if (windowManager == null) {
            return null;
        }
        final int[] result = new int[]{0, 0};
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        result[0] = point.x;
        result[1] = point.y;
        return result;
    }

    /**
     * 手势回调
     */
    public class PlayerGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean mFirstTouch;
        private boolean mVolumeControl;

        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            ijkVideoView.toggleAspectRatio();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mFirstTouch = true;
            return super.onDown(e);
        }

        //滑动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            float deltaY = mOldY - e2.getY();
            if (mFirstTouch) {
                mVolumeControl = mOldX > mWidthPixels * 0.5f;
                mFirstTouch = false;
            }
            float percent = deltaY / ijkVideoView.getWidth();
            if (mVolumeControl) {
                onVolumeSlide(percent);
            } else {
                if (activity != null)
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

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    @SuppressLint("SetTextI18n")
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f) {
                mBrightness = 0.50f;
            } else if (mBrightness < 0.01f) {
                mBrightness = 0.01f;
            }
        }
        Log.d(this.getClass().getSimpleName(), "mBrightness:" + mBrightness + ",percent:" + percent);
        findViewById(R.id.app_video_brightness_box).setVisibility(VISIBLE);
        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        app_video_brightness.setText(((int) (lpa.screenBrightness * 100)) + "%");
        activity.getWindow().setAttributes(lpa);
    }

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
        volumeImg.setImageResource(i == 0 ? R.mipmap.ic_volume_off_white_36dp : R.mipmap.ic_volume_up_white_36dp);
        findViewById(R.id.app_video_brightness_box).setVisibility(View.GONE);
        findViewById(R.id.app_video_volume_box).setVisibility(View.VISIBLE);
        app_video_volume.setText(s);
        app_video_volume.setVisibility(View.VISIBLE);
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
                        if (null != ijkVideoView) {
                            ijkVideoView.seekTo(newPosition);
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

            if (!mInstantSeeking && null != ijkVideoView && mDuration > 0) {
                ijkVideoView.seekTo((int) (mDuration * seekBar.getProgress()) / 1000);
            }
            if (null != netMonitorHandler) {
                netMonitorHandler.removeMessages(MSG_SHOW_PROGRESS);
                netMonitorHandler.sendEmptyMessageDelayed(MSG_SHOW_PROGRESS, 1000);
            }
        }
    };

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


    @SuppressLint("HandlerLeak")
    class HyperHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null == msg) {
                return;
            }
            switch (msg.what) {
                case MESSAGE_HIDE_CENTER_BOX:
                    findViewById(R.id.app_video_volume_box).setVisibility(View.GONE);
                    findViewById(R.id.app_video_brightness_box).setVisibility(View.GONE);
                    findViewById(R.id.app_video_fastForward_box).setVisibility(View.GONE);
                    break;
                case MSG_SHOW_PROGRESS:
                    if (!mDragging && null != mVideoPlayerControlFrame
                            && mVideoPlayerControlFrame.getVisibility() == View.VISIBLE) {
                        msg = obtainMessage(MSG_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (setVideoProgress() % 1000));
                    }
                    break;

                case MSG_SHOW_ERROR_DIALOG:
                    if (null != msg.obj
                            && !TextUtils.isEmpty(msg.obj.toString())) {
                        Log.e("播放器错误", msg.obj.toString());
                        onCallBack.error("播放器错误:" + msg.obj.toString());
                    }
                    break;
            }
        }
    }

    private long setVideoProgress() {
        if (ijkVideoView == null || mDragging)
            return 0;
        long position = ijkVideoView.getCurrentPosition();
        long duration = ijkVideoView.getDuration();
        long pos = 0;
        if (mVideoPlayerSeekBar != null) {
            if (duration > 0) {
                pos = 1000L * position / duration;
                mVideoPlayerSeekBar.setProgress((int) pos);
            }
            int percent = ijkVideoView.getBufferPercentage() * 10;
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

    /**
     * 获取当前播放百分比
     *
     * @return 百分比数字
     */
    private int getPlayPercentage() {
        long position = 0l;
        long duration = 0l;

        if (null != ijkVideoView) {
            position = ijkVideoView.getCurrentPosition();
            duration = ijkVideoView.getDuration();
        }
        if (duration > 0) {
            return (int) (100L * position / duration);
        } else {
            return 0;
        }
    }

    /**
     * 设置视频随屏幕横竖屏
     *
     * @param isFull
     */
    private void setViewPlayerScreen(boolean isFull) {
        if (isFull) {
            this.isFull = isFull;
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            mViewHeight = this.getHeight();
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            this.setLayoutParams(lp);
        } else {
            this.isFull = isFull;
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
            lp.height = mViewHeight;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            this.setLayoutParams(lp);
        }
    }


    /**
     * 暂停和播放
     */
    private void play(boolean needSeek) {
        if (null != ijkVideoView && !ijkVideoView.isPlaying()) {
            ijkVideoView.start();
            onCallBack.startPlay();
            if (mLastPosition >= 0 && needSeek) {
                ijkVideoView.seekTo(mLastPosition);
            }
        }
        if (null != mVideoPlayerPauseResume) {
            mVideoPlayerPauseResume.setImageDrawable(getResources().
                    getDrawable(R.mipmap.ic_white_player_pause));
        }
    }

    /**
     * 播放器暂停
     */
    private void pause() {
        if (null != ijkVideoView
                && ijkVideoView.canPause()) {
            ijkVideoView.pause();
            mLastPosition = ijkVideoView.getCurrentPosition();
        }
        if (null != mVideoPlayerPauseResume) {
            mVideoPlayerPauseResume.setImageDrawable(getResources().
                    getDrawable(R.mipmap.ic_white_player_play));
        }
    }


    /**
     * 播放视频
     *
     * @param url 视频路径
     */
    public void play(String url) {
        mApp_video_loading.setVisibility(View.VISIBLE);
        ijkVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        ijkVideoView.setVideoURI(Uri.parse(url));
        ijkVideoView.start();
        setScaleType();
    }

    /**
     * 播放视频
     *
     * @param url 视频路径
     */
    public void playNew(String url) {
        onDestroy();
        mApp_video_loading.setVisibility(View.VISIBLE);
        mDuration = 0;
        ijkVideoView.setVideoURI(Uri.parse(url));
        ijkVideoView.start();
        setScaleType();
    }

    public void onDestroy() {
        //重置播放器
        //换源之后声音可播，画面卡住，主要是渲染问题，目前只是提供了软解方式
        ijkVideoView.setRender(ijkVideoView.RENDER_TEXTURE_VIEW);
        if (ijkVideoView == null) return;
        ijkVideoView.stopPlayback();
        ijkVideoView.release(true);

    }

    protected void onStop() {
        onCallBack.stop();
        if (null != netMonitorHandler) {
            netMonitorHandler.removeCallbacksAndMessages(null);
            netMonitorHandler = null;
        }

        if (!ijkVideoView.isBackgroundPlayEnabled()) {
            ijkVideoView.stopPlayback();
            ijkVideoView.release(true);
            ijkVideoView.stopBackgroundPlay();
        } else {
            ijkVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
        mLastPosition = 0;
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return ijkVideoView != null ? ijkVideoView.isPlaying() : false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mVideoPlayerBack:
                onStop();
                activity.finish();
                break;
            //显示或隐藏控制面板
            case R.id.mVideoPlayerControlFrame:
                controlFrameOnClick();
                break;
            //屏幕方向切换
            case R.id.mVideoPlayerDirection:
                if (isFull == false) {
                    setViewPlayerScreen(true);
                } else {
                    setViewPlayerScreen(false);
                }
                break;
            //暂停和播放
            case R.id.mVideoPlayerPauseResume:
                if (null != ijkVideoView) {
                    if (ijkVideoView.isPlaying()) {
                        pause();
                        onCallBack.pause();
                    } else {
                        play(false);
                        onCallBack.play();
                    }
                }
                break;
        }
    }

    /**
     * <pre>
     *     fitParent:可能会剪裁,保持原视频的大小，显示在中心,当原视频的大小超过view的大小超过部分裁剪处理
     *     fillParent:可能会剪裁,等比例放大视频，直到填满View为止,超过View的部分作裁剪处理
     *     wrapContent:将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中
     *     fitXY:不剪裁,非等比例拉伸画面填满整个View
     *     16:9:不剪裁,非等比例拉伸画面到16:9,并完全显示在View中
     *     4:3:不剪裁,非等比例拉伸画面到4:3,并完全显示在View中
     * </pre>
     *
     * @param scaleType
     */

    public void setScreenScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

    public void setScaleType() {
        if (scaleType != null) {
            if (SCALETYPE_FITPARENT.equals(scaleType)) {
                ijkVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
            } else if (SCALETYPE_FILLPARENT.equals(scaleType)) {
                ijkVideoView.setAspectRatio(IRenderView.AR_ASPECT_FILL_PARENT);
            } else if (SCALETYPE_WRAPCONTENT.equals(scaleType)) {
                ijkVideoView.setAspectRatio(IRenderView.AR_ASPECT_WRAP_CONTENT);
            } else if (SCALETYPE_FITXY.equals(scaleType)) {
                ijkVideoView.setAspectRatio(IRenderView.AR_MATCH_PARENT);
            } else if (SCALETYPE_16_9.equals(scaleType)) {
                ijkVideoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
            } else if (SCALETYPE_4_3.equals(scaleType)) {
                ijkVideoView.setAspectRatio(IRenderView.AR_4_3_FIT_PARENT);
            }
        }
    }

    onCallBack onCallBack;

    public void setOnCallBack(Activity activity, onCallBack callBack) {
        this.activity = activity;
        onCallBack = callBack;
    }

    /**
     * 回调相关
     */
    public interface onCallBack {
        //第一次播放
        void startPlay();

        //暂停
        void pause();

        //播放
        void play();

        //播放错误
        void error(String error);

        //视频播放结束
        void playEnd();

        //视频关闭
        void stop();
    }

}
