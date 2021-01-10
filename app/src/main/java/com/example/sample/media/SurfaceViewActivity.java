package com.example.sample.media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class SurfaceViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button playBtn, pauseBtn, stopBtn;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private boolean manualStopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_surface_view);

        playBtn = findViewById(R.id.play);
        pauseBtn = findViewById(R.id.pause);
        stopBtn = findViewById(R.id.stopAudio);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);

        surfaceView = findViewById(R.id.surface);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.setKeepScreenOn(true);
        holder.addCallback(new SurfaceCallback());
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.play:
                play();
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playBtn.setEnabled(true);
                }
                break;
            case R.id.stopAudio:
                mediaPlayer.stop();
                manualStopped = true;
                playBtn.setEnabled(true);
                break;
            default:
                break;
        }
    }

    private void play() {
        playBtn.setEnabled(false);

        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            } else if (manualStopped) {
                mediaPlayer.prepare();
            }
            mediaPlayer.start();
            // 这一句不能忽视
            manualStopped = false;
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(e);
            playBtn.setEnabled(true);
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer.reset();
            try {
                // 注意非标准的 mp4、3GP 文件将无法播放，建议播放手机录制的视频
                // 1.有效
                // AssetFileDescriptor assetFileDescriptor = getAssets().openFd("V01122_191113.mp4");
                // AssetFileDescriptor assetFileDescriptor = getAssets().openFd("VID_20201007_153147.mp4");
                // mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                // 2.无效 TODO
                // mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "V01122_191113.mp4");
                // 3.无效 TODO
                // mediaPlayer.setDataSource( "/mnt/sdcard/V01122_191113.mp4");
                // 网络视频
                // 部分 https 网络资源无法播放
                // 以下网络视频有时效性，失效时请替换可以播放的网络视频地址
                // mediaPlayer.setDataSource("https://haokan.baidu.com/v?vid=6829734555796860704&pd=bjh&fr=bjhauthor&type=video");
                mediaPlayer.setDataSource("http://f.video.weibocdn.com/Q7YyTBc8lx07I0PV0EeA01041204N0tY0E020.mp4?label=mp4_720p&template=1280x720.25.0&trans_finger=775cb0ab963a74099cf9f840cd1987f1&media_id=4571422014504967&tp=8x8A3El:YTkl0eM8&us=0&ori=1&bf=3&ot=h&ps=3lckmu&uid=3ZoTIp&ab=966-g1,3370-g1,1493-g0,1192-g0,1191-g1,1258-g0&Expires=1606071068&ssig=HoDQqsz8Ok&KID=unistore,video");
                mediaPlayer.setDisplay(surfaceView.getHolder());
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(mp -> {
                    playBtn.setEnabled(true);
                    manualStopped = false;
                });

                // 设置视频高宽，宽度与窗口同宽，高度根据宽度缩放比例来缩放
                WindowManager windowManager = getWindowManager();
                DisplayMetrics metrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(metrics);
                surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels, mediaPlayer.getVideoHeight() * metrics.widthPixels / mediaPlayer.getVideoWidth()));
            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(e);
            }
            play();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    @Override
    protected void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
}