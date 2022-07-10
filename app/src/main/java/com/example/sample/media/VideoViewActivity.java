package com.example.sample.media;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        }
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video_view);

        VideoView videoView = findViewById(R.id.video);
        // 非标准的 mp4、3GP 文件将无法播放，建议播放手机录制的视频
        // TODO 目前手机录制的视频暂不能播放
        // videoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "VID_20201007_153147.mp4");
        // 播放网络视频
        // 部分 https 网络资源无法播放
        // 以下网络视频有时效性，失效时请替换可以播放的网络视频地址
        videoView.setVideoURI(Uri.parse("http://f.video.weibocdn.com/Q7YyTBc8lx07I0PV0EeA01041204N0tY0E020.mp4?label=mp4_720p&template=1280x720.25.0&trans_finger=775cb0ab963a74099cf9f840cd1987f1&media_id=4571422014504967&tp=8x8A3El:YTkl0eM8&us=0&ori=1&bf=3&ot=h&ps=3lckmu&uid=3ZoTIp&ab=966-g1,3370-g1,1493-g0,1192-g0,1191-g1,1258-g0&Expires=1606071068&ssig=HoDQqsz8Ok&KID=unistore,video"));
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
    }
}