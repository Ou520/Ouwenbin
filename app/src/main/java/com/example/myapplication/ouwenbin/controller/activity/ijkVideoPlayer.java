package com.example.myapplication.ouwenbin.controller.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.utils.MyPopupWindow;
import com.example.myapplication.ouwenbin.utils.Utils;
import com.example.myapplication.ouwenbin.utils.view.MyTextView;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class ijkVideoPlayer extends Activity {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.media_controller)
    RelativeLayout media_controller;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_networkType)
    TextView tvNetworkType;
    @BindView(R.id.tv_videoName)
    MyTextView tvVideoName;
    @BindView(R.id.tv_system_time)
    TextView tvSystemTime;
    @BindView(R.id.iv_battery)
    ImageView ivBattery;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.iv_tread)
    ImageView ivTread;
    @BindView(R.id.iv_coin)
    ImageView ivCoin;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_menus)
    ImageView ivMenus;
    @BindView(R.id.seekbar_video)
    SeekBar seekbarVideo;
    @BindView(R.id.iv_pause)
    ImageView ivPause;
    @BindView(R.id.tv_playingCourse)
    TextView tvPlayingCourse;
    @BindView(R.id.iv_danmuguang)
    ImageView ivDanmuguang;
    @BindView(R.id.iv_danmakuSet)
    ImageView ivDanmuSet;
    @BindView(R.id.bnt_addDanmaku)
    Button bntAddDanmaku;
    @BindView(R.id.tv_resolutionRatio)
    TextView tvResolutionRatio;
    @BindView(R.id.ic_camera)
    ImageView icCamera;
    @BindView(R.id.ic_lock_Left)
    ImageView icLockLeft;
    //视频的总时长
    private int duration;
    //工具类
    private Utils utils;
    //弹窗工具类
    private MyPopupWindow myPopupWindow;
    //媒体播放器
    private IjkMediaPlayer ijkMediaPlayer;
    //传入进来的视频列表
    private ArrayList<MediaItem> mediaItems;
    //要播放的列表中的具体位置
    private int position;
    //数据模型
    private MediaItem mediaItem;
    private GestureDetector detector;
    //定义屏幕宽高的变量
    private int mScreenWidth;
    private int mScreenHeight;
    //是否显示控制面板
    private boolean isshowMediaController = false;
    //视频进度的更新
    private static final int PROGRESS = 1;
    //1.得到当前的播放进度
    private int currentPosition;
    // 监听电量变化的广播
    private MyReceiver receiver;
    private  boolean DanmakuShow = false;//设置是否显示弹幕


    //接收消息，并进行相应的操作
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2://100毫秒
                    dismissVolumeDialog();
                    break;
                case PROGRESS:
                    //1.得到当前的播放进度
                    currentPosition = (int) ijkMediaPlayer.getCurrentPosition();
                    //2.给SeekBar设置当前视频进度
                    seekbarVideo.setProgress(currentPosition);
                    //更新播放进度
                    tvPlayingCourse.setText(utils.stringForTime(currentPosition) + " / " + utils.stringForTime(duration));
                    //设置系统时间
                    String time = utils.getSystemTime();
                    tvSystemTime.setText(time);
                    //3.每秒更新一次
                    removeMessages(PROGRESS);
                    sendEmptyMessageDelayed(PROGRESS, 1000);
                    break;
                case 3://3秒
                    hideMediaController();//隐藏控制面板
                    break;

            }
        }
    };


    //显示控制面板
    private void showMediaController() {
        media_controller.setVisibility(View.VISIBLE);
        handler.removeMessages(3);
        handler.sendEmptyMessageDelayed(3, 3000);
        isshowMediaController = true;
    }

    //隐藏控制面板
    private void hideMediaController() {
        media_controller.setVisibility(View.GONE);
        isshowMediaController = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        getData();
        setData();
        setListener();
    }


    private void initData() {
        setContentView(R.layout.activity_ijk_video_player);
        ButterKnife.bind(this);
        //注册电量广播
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        //当电量变化的时候发广播
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, intentFilter);
    }

    //通过广播来获取系统电量的变化情况，并设置电池图标的变化
    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);//0-100
            utils.setBattery(level, ivBattery);//设置电池图片的变化
        }
    }


    private void getData() {
        //初始化工具类
        utils = new Utils();
        myPopupWindow = new MyPopupWindow(ijkVideoPlayer.this,media_controller);
        //接收数据
        mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        position = getIntent().getIntExtra("position", 0);

        //获取屏幕的高度和宽度
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

    }


    private void setData() {
        //为VideoView设置地址
        if (mediaItems != null && mediaItems.size() > 0) {
            mediaItem = mediaItems.get(position);
            tvVideoName.setText(mediaItem.getName());//设置视频的名称
        } else {
            Toast.makeText(this, "地址为空,没有数据！", Toast.LENGTH_SHORT).show();
        }
        tvNetworkType.setText(utils.getNetworkType(this));
        surfaceView.getHolder().addCallback(callback);//SurfaceView设置回调

    }

    private void setListener() {
        //实例化手势识别器，并且重写双击，单击，长按
        detector = new GestureDetector(this, new MyOnGestureListener());
        //设置视频进度的SeekBar的状态监听
        seekbarVideo.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());
    }

    //视频进度的SeekBar的拖动监听
    class VideoOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        //当手指滑动的时候，会引起SeekBar进度变化时回调这个方法
        //fromUser：如果是用户引起的返回true，不是用户引起的返回false
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                ijkMediaPlayer.seekTo(progress);
            }
        }

        //当手指触碰的时候回调这个方法
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(3);

        }

        //当手指离开的时候回调这个方法
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(3, 3000);
        }
    }

    //控制面板按钮的点击事件
    @OnClick({R.id.iv_return, R.id.iv_like, R.id.iv_tread, R.id.iv_coin, R.id.iv_share, R.id.iv_menus,R.id.ic_camera, R.id.ic_lock_Left, R.id.iv_pause, R.id.iv_danmuguang, R.id.iv_danmakuSet, R.id.bnt_addDanmaku, R.id.tv_resolutionRatio})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return://返回
                finish();
                break;
            case R.id.iv_like://点赞
                if (ivTreadStatus) {
                    Toast.makeText(this, "点赞", Toast.LENGTH_SHORT).show();
                    ivLikeStatus();
                }
                break;
            case R.id.iv_tread://不点赞
                if (ivLikeStatus) {
                    Toast.makeText(this, "不点赞", Toast.LENGTH_SHORT).show();
                    ivTreadStatus();
                }
                break;
            case R.id.iv_coin://硬币
                Toast.makeText(this, "硬币", Toast.LENGTH_SHORT).show();
                //隐藏控制面板
                hideMediaController();
                myPopupWindow.PopupWindow("Coin");
                break;
            case R.id.iv_share://分享
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                //隐藏控制面板
                hideMediaController();
                myPopupWindow.PopupWindow("Share");
                break;
            case R.id.iv_menus://菜单
                Toast.makeText(this, "菜单", Toast.LENGTH_SHORT).show();
                //隐藏控制面板
                hideMediaController();
                myPopupWindow.PopupWindow("Menus");
                break;
            case R.id.ic_camera://截屏
                Toast.makeText(this, "截屏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ic_lock_Left://锁屏
                Toast.makeText(this, "锁屏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_pause://视频的播放和暂停
                //视频的播放和暂停
                StarAndPause();
                break;
            case R.id.iv_danmuguang://关闭弹幕
                Toast.makeText(this, "关闭弹幕", Toast.LENGTH_SHORT).show();
                DanmakuHideAndShow();
                break;
            case R.id.iv_danmakuSet://弹幕设置
                Toast.makeText(this, "弹幕设置", Toast.LENGTH_SHORT).show();
                //隐藏控制面板
                hideMediaController();
                myPopupWindow.PopupWindow("DanmakuSet");
                break;
            case R.id.bnt_addDanmaku://发送弹幕
                //隐藏控制面板
                hideMediaController();
                myPopupWindow.PopupWindow("AddDanmaku");
                break;
            case R.id.tv_resolutionRatio://选择分辨率
                Toast.makeText(this, "选择分辨率", Toast.LENGTH_SHORT).show();
                //隐藏控制面板
                hideMediaController();
                myPopupWindow.PopupWindow("ResolutionRatio");
                break;
        }
    }



    /*--------------------------------------------------控制面板按钮的操作的方法----------------------------------------------------------------------------------*/

    //点赞按钮的状态
    private boolean ivLikeStatus = true;

    private void ivLikeStatus() {
        //显示控制面板
        showMediaController();
        if (ivLikeStatus) {
            ivLike.setImageResource(R.drawable.ic_like_t);
            ivLikeStatus = false;
        } else {
            ivLike.setImageResource(R.drawable.ic_like_f);
            ivLikeStatus = true;
        }
    }

    //不点赞按钮的状态
    private boolean ivTreadStatus = true;

    private void ivTreadStatus() {
        if (ivTreadStatus) {
            //显示控制面板
            showMediaController();
            ivTread.setImageResource(R.drawable.ic_tread_t);
            ivTreadStatus = false;
        } else {
            //显示控制面板
            showMediaController();
            ivTread.setImageResource(R.drawable.ic_tread_f);
            ivTreadStatus = true;
        }
    }

    //播放和暂停
    private void StarAndPause() {
        //显示控制面板
        showMediaController();
        if (ijkMediaPlayer.isPlaying()) {
            ijkMediaPlayer.pause();
            //按钮状态设置为播放
            ivPause.setImageResource(R.drawable.ic_play);

        } else {
            ijkMediaPlayer.start();
            //按钮的状态设置为暂停
            ivPause.setImageResource(R.drawable.ic_pause);
        }
    }

    //弹幕的显示和隐藏
    private void DanmakuHideAndShow() {
        //显示控制面板
        showMediaController();
        if (DanmakuShow) {
            DanmakuShow = false;
            ivDanmuguang.setImageResource(R.drawable.ic_danmukai);
            bntAddDanmaku.setVisibility(View.VISIBLE);
            ivDanmuSet.setVisibility(View.VISIBLE);
        } else {
            DanmakuShow = true;
            ivDanmuguang.setImageResource(R.drawable.ic_danmuguang);
            bntAddDanmaku.setVisibility(View.INVISIBLE);
            ivDanmuSet.setVisibility(View.INVISIBLE);
        }
    }
    //--------------------------------------surfaceView的回调方法---------------------------------------------
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        //当surface第一次创建的时候，这个方法就会被立即调用。这个方法的实现可以完成surface创建后的一些初始化工作。
        public void surfaceCreated(SurfaceHolder holder) {
            createPlayer();//创建播放器，并初始化
            ijkMediaPlayer.setDisplay(surfaceView.getHolder());//设置视频显示视图

            //应用切换至后台时调用播放器pause挂起播放
            if (ijkMediaPlayer != null) {
                ijkMediaPlayer.pause();
            }
        }

        @Override

        //当surface的任何结构（格式或大小）发生改变，这个方法就立即被调用。你应该在此刻更新surface。这个方法至少会被调用一次，在surfaceCreated(SurfaceHolder).调用之后。
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //应用切换至前台时调用播放器start继续播放。
            if (ijkMediaPlayer != null) {
                ijkMediaPlayer.start();
            }

        }

        @Override
        //在一个surface被销毁前，这个方法会被调用。在这个调用返回后，你再也不应该去访问surface了。
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (surfaceView != null) {
                surfaceView.getHolder().removeCallback(callback);
                surfaceView = null;
            }
        }
    };

    //---------------------------------------创建播放器，并初始化-------------------------------------------------------------
    private void createPlayer() {
        if (ijkMediaPlayer == null) {
            ijkMediaPlayer = new IjkMediaPlayer();

            //IjkPlayer播放器秒开优化以及常用Option设置
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", 1);//设置播放前的探测时间 1,达到首屏秒开效果
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max_cached_duration", 3000);// 最大缓存大小是3秒，可以根据实际需求进行修改。
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "infbuf", 1);//无限读。
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);//关闭播放器缓冲。
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 5);//跳帧处理,放CPU处理较慢时，进行跳帧处理，保证播放流程，画面和声音同步


            try {

                ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                ijkMediaPlayer.setScreenOnWhilePlaying(true);//设置屏幕常亮
                //设置播放地址
                String url = mediaItems.get(position).getData();
                ijkMediaPlayer.setDataSource(url);
                ijkMediaPlayer.prepareAsync();//异步准备播放

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                duration = (int) iMediaPlayer.getDuration();
                seekbarVideo.setMax(duration);
                //2.发消息
                handler.sendEmptyMessage(PROGRESS);
            }
        });//播放器异步准备监听
    }


//--------------------------------------------------Activity的触摸事件--------------------------------------------------------------------------------

    protected float mDownX;//手指一点击屏幕时X的指标
    protected float mDownY;//手指一点击屏幕时Y的指标
    protected boolean mChangeVolume;//音量
    protected boolean mChangePosition;//视频进度
    public static final int THRESHOLD = 80;//滑动的距离的参照
    protected boolean mChangeBrightness;//亮度

    protected long mGestureDownPosition;//获取视频的当前进度
    protected int mGestureDownVolume;//获取当前的音量
    protected float mGestureDownBrightness;//获取当前的亮度
    protected long mSeekTimePosition;//改变后视频的进度
    protected AudioManager mAudioManager;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把事件传递个手势识别器
        detector.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            //手指一点击屏幕时的监听
            case MotionEvent.ACTION_DOWN:

                mDownX = x;
                mDownY = y;
                mChangeVolume = false;
                mChangePosition = false;
                mChangeBrightness = false;

                break;
            //手指滑动屏幕时的监听
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mDownX;//X轴滑动的距离
                float deltaY = y - mDownY;//Y轴滑动的距离
                float absDeltaX = Math.abs(deltaX);//该方法返回deltaX的绝对值
                float absDeltaY = Math.abs(deltaY);//该方法返回deltaY的绝对值

                if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                    if (absDeltaX >= THRESHOLD) {

                        mChangePosition = true;//设置视频进度
                        mGestureDownPosition = ijkMediaPlayer.getCurrentPosition();//获取视频的当前进度


                    } else {
                        if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
                            mChangeBrightness = true;//设置亮度
                            WindowManager.LayoutParams lp = utils.getWindow(this).getAttributes();
                            if (lp.screenBrightness < 0) {
                                try {
                                    mGestureDownBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mGestureDownBrightness = lp.screenBrightness * 255;
                            }
                        } else {//右侧改变声音

                            mChangeVolume = true;//设置声音
                            mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前的音量
                        }
                    }
                }
//--------------------------左右滑动改变视频的进度----------------------------------
                if (mChangePosition) {
                    long totalTimeDuration = ijkMediaPlayer.getDuration();//获取视频的总长度
                    mSeekTimePosition = (int) (mGestureDownPosition + (deltaX / 5) * totalTimeDuration / mScreenWidth);
                    if (mSeekTimePosition > totalTimeDuration)
                        mSeekTimePosition = totalTimeDuration;
                    String seekTime = utils.stringForTime(mSeekTimePosition);
                    String totalTime = utils.stringForTime(totalTimeDuration);

                    showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                }

//--------------------------右侧上下滑动改变音量----------------------------------
                if (mChangeVolume) {
                    deltaY = -deltaY / 20;
                    int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                    //设置音量（当前+改变）
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);

                    //dialog中显示百分比
                    int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 100 / mScreenHeight);
                    showVolumeDialog(volumePercent, true);
                }

//--------------------------设置亮度--------------------------------------
                if (mChangeBrightness) {
                    deltaY = -deltaY / 20;
                    int deltaV = (int) (255 * deltaY / mScreenHeight);
                    WindowManager.LayoutParams params = utils.getWindow(this).getAttributes();
                    if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                        params.screenBrightness = 1;
                    } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                        params.screenBrightness = 0.01f;
                    } else {
                        params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
                    }
                    utils.getWindow(this).setAttributes(params);

                    //dialog中显示百分比
                    int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 100 / mScreenHeight);
                    showBrightnessDialog(brightnessPercent);
                }

                break;
            //手指离开屏幕时的监听
            case MotionEvent.ACTION_UP:
                dismissProgressDialog();
                dismissVolumeDialog();
                dismissBrightnessDialog();

                if (mChangePosition) {
                    //设置视频的进度
                    ijkMediaPlayer.seekTo(mSeekTimePosition);
                    //获取视频的总时间
                    long duration = ijkMediaPlayer.getDuration();
                    int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
                    //设置控制面板进度条的位置
                    seekbarVideo.setProgress(progress);
                }

                break;
        }

        return super.onTouchEvent(event);
    }


    //实例化手势识别器，并且重写双击，单击，长按
    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            StarAndPause();//播放和暂停
            return super.onDoubleTap(e);
        }

        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //如果是显示就隐藏。如果隐藏就显示。（控制面板）
            if (isshowMediaController) {
                //隐藏
                hideMediaController();
                //把隐藏消息移除
                handler.removeMessages(3);
            } else {
                //显示控制面板
                showMediaController();
            }
            return super.onSingleTapConfirmed(e);
        }

    }


    //-------------------------------视频进度的对话框---------------------------------------------------------
    protected Dialog mProgressDialog;
    private ProgressBar mDialogProgressBar;
    private TextView mDialogSeekTime;
    private TextView mDialogTotalTime;
    private ImageView mDialogIcon;

    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
        if (mProgressDialog == null) {
            View localView = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
            mDialogProgressBar = localView.findViewById(R.id.duration_progressbar);
            mDialogSeekTime = localView.findViewById(R.id.tv_current);
            mDialogTotalTime = localView.findViewById(R.id.tv_duration);
            mDialogIcon = localView.findViewById(R.id.duration_image_tip);
            mProgressDialog = createDialogWithView(localView);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        mDialogSeekTime.setText(seekTime);
        mDialogTotalTime.setText(" / " + totalTime);
        mDialogProgressBar.setProgress(totalTimeDuration <= 0 ? 0 : (int) (seekTimePosition * 100 / totalTimeDuration));
        if (deltaX > 0) {
            mDialogIcon.setBackgroundResource(R.drawable.jz_forward_icon);
        } else {
            mDialogIcon.setBackgroundResource(R.drawable.jz_backward_icon);
        }

    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    //-------------------------------音量的对话框---------------------------------------------------------
    protected Dialog mVolumeDialog;
    private ImageView mDialogVolumeImageView;
    private TextView mDialogVolumeTextView;
    private ProgressBar mDialogVolumeProgressBar;

    public void showVolumeDialog(int volumePercent, boolean biaoji) {
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(this).inflate(R.layout.dialog_volume, null);
            mDialogVolumeImageView = localView.findViewById(R.id.volume_image_tip);
            mDialogVolumeTextView = localView.findViewById(R.id.tv_volume);
            mDialogVolumeProgressBar = localView.findViewById(R.id.volume_progressbar);
            mVolumeDialog = createDialogWithView(localView);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }
        if (biaoji) {
            if (volumePercent <= 0) {
                mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_close_volume);
            } else {
                mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_add_volume);
            }
            if (volumePercent > 100) {
                volumePercent = 100;
            } else if (volumePercent < 0) {
                volumePercent = 0;
            }
            mDialogVolumeTextView.setText(volumePercent + "%");
            mDialogVolumeProgressBar.setProgress(volumePercent);
        } else {
            int volumePercent1 = volumePercent * 2;
            if (volumePercent1 <= 0) {
                mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_close_volume);
            } else {
                mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_add_volume);
            }
            if (volumePercent1 > 100) {
                volumePercent1 = 100;
            } else if (volumePercent1 < 0) {
                volumePercent1 = 0;
            }
            mDialogVolumeTextView.setText(volumePercent1 + "%");
            mDialogVolumeProgressBar.setProgress(volumePercent1);

        }

    }

    public void dismissVolumeDialog() {
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
        }
    }

    //-------------------------------亮度的对话框---------------------------------------------------------
    protected Dialog mBrightnessDialog;
    private TextView mDialogBrightnessTextView;
    private ProgressBar mDialogBrightnessProgressBar;

    public void showBrightnessDialog(int brightnessPercent) {
        if (mBrightnessDialog == null) {
            View localView = LayoutInflater.from(this).inflate(R.layout.dialog_brightness, null);
            mDialogBrightnessTextView = localView.findViewById(R.id.tv_brightness);
            mDialogBrightnessProgressBar = localView.findViewById(R.id.brightness_progressbar);
            mBrightnessDialog = createDialogWithView(localView);
        }
        if (!mBrightnessDialog.isShowing()) {
            mBrightnessDialog.show();
        }
        if (brightnessPercent > 100) {
            brightnessPercent = 100;
        } else if (brightnessPercent < 0) {
            brightnessPercent = 0;
        }
        mDialogBrightnessTextView.setText(brightnessPercent + "%");
        mDialogBrightnessProgressBar.setProgress(brightnessPercent);
    }

    public Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(this, R.style.style_dialog_progress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        window.addFlags(Window.FEATURE_ACTION_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.setLayout(-2, -2);
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(localLayoutParams);
        return dialog;
    }

    //隐藏亮度对话框
    public void dismissBrightnessDialog() {
        if (mBrightnessDialog != null) {
            mBrightnessDialog.dismiss();
        }
    }


    /*---------------------------------------Activity的生命周期方法-------------------------------------------------------*/


    //监听物理键实现声音大小的变化
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前的音量
            mGestureDownVolume--;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume, 0);
            showVolumeDialog(mGestureDownVolume, false);
            handler.removeMessages(2);
            handler.sendEmptyMessageDelayed(2, 100);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前的音量
            mGestureDownVolume++;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume, 0);
            showVolumeDialog(mGestureDownVolume, false);
            handler.removeMessages(2);
            handler.sendEmptyMessageDelayed(2, 100);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    //释放资源
    protected void onDestroy() {
        super.onDestroy();
        release();//释放ijkMediaPlayer的资源
        //注销广播
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        //移除消息
        handler.removeMessages(2);
        handler.removeMessages(PROGRESS);
    }

    //释放ijkMediaPlayer的资源
    private void release() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }
}
