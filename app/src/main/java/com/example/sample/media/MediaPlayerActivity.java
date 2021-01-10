package com.example.sample.media;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayer resPlayer;
    private Button resBtn;
    private boolean resStopped = false;
    private MediaPlayer assetsPlayer;
    private Button assetsBtn;
    private boolean assetsStopped = false;
    private MediaPlayer storagePlayer;
    private Button storageBtn;
    private boolean storageStopped = false;
    private MediaPlayer internetPlayer;
    private Button internetBtn;
    private boolean internetStopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        // res audio
        resBtn = findViewById(R.id.playRes);

        // assets audio
        assetsBtn = findViewById(R.id.playAssets);
        assetsPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("treasure.mp3");
            assetsPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetsPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            assetsPlayer.prepare();
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
        assetsPlayer.setOnCompletionListener(mp -> {
            assetsBtn.setEnabled(true);
            assetsStopped = false;
        });

        // sdcard audio
        storageBtn = findViewById(R.id.playStorage);
        storagePlayer = new MediaPlayer();
        // storagePlayer.reset();
        // storagePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // String path = Uri.parse("/mnt/sdcard/sun").getPath();
        try {
            // ActivityCompat.requestPermissions(this, new String[]{
            //         Manifest.permission.WRITE_EXTERNAL_STORAGE
            // }, 1);
            // TODO setDataSource 逻辑目前有问题，用什么方法都读取不到 SD 卡中的音频文件
            storagePlayer.setDataSource("/sdcard/12530/download/Treasure-Bruno Mars.mp3");
            storagePlayer.prepare();
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
        storagePlayer.setOnCompletionListener(mp -> {
            storageBtn.setEnabled(true);
            storageStopped = false;
        });

        // network audio
        internetBtn = findViewById(R.id.playInternet);
        internetPlayer = new MediaPlayer();
        // 以下网络音频有时效，使用时可能需要修改
        // 部分 https 网络资源无法播放
        // 暂停、播放完、停止后，再播放不需要再次 setDataSource，故只需要在 onCreate 中执行一次 setDataSource 即可
        String networkPath = "https://s.aigei.com/pvaud/aud/mp3/86/8673a76ab79a4ad9a49bef00b5646b72.mp3?e=1606086840&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:lRy7pLxgZ9mej6gIn9k6pw3GU08=";
        // String networkPath = "http://211.162.127.3/files/309200000807517B/freetyst.nf.migu.cn/public/product9th/product41/2020/08/1013/2009%E5%B9%B406%E6%9C%8826%E6%97%A5%E5%8D%9A%E5%B0%94%E6%99%AE%E6%96%AF/%E6%A0%87%E6%B8%85%E9%AB%98%E6%B8%85/MP3_320_16_Stero/60054701921134458.mp3";
        // String networkPath = "https://m801.music.126.net/20201121235639/3fff0b4fd902ea85b2d106938fd24c20/jdyyaac/540b/010b/525d/ca0432bad36af4122c25e7582acf3431.m4a";
        try {
            internetPlayer.setDataSource(networkPath);
            // internetPlayer.setDataSource(this, Uri.parse(networkPath));

            // 首次播放需要 prepare，停止后也需要 prepare，但是播放完再播放不需要 prepare
            internetPlayer.prepare();
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
        internetPlayer.setOnCompletionListener(mp -> {
            internetBtn.setEnabled(true);
            internetStopped = false;
        });
    }

    public void playRes(View view) {
        resBtn.setEnabled(false);
        try {
            if (resPlayer != null && (resPlayer.isPlaying() || !resStopped)) {
                resPlayer.seekTo(resPlayer.getCurrentPosition());
            } else {
                resPlayer = MediaPlayer.create(this, R.raw.glass_zh);
                resPlayer.setOnCompletionListener(mp -> {
                    resBtn.setEnabled(true);
                    resPlayer = null;
                });
            }
            resPlayer.start();
            resStopped = false;
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
            resBtn.setEnabled(true);
        }
    }

    public void pauseRes(View view) {
        if (resPlayer == null || !resPlayer.isPlaying()) {
            return;
        }
        resPlayer.pause();
        resBtn.setEnabled(true);
    }

    public void stopRes(View view) {
        if (resPlayer == null) {
            return;
        }
        resPlayer.stop();
        resStopped = true;
        resBtn.setEnabled(true);
    }

    public void playAssets(View view) {
        assetsBtn.setEnabled(false);
        try {
            if (assetsPlayer.isPlaying()) {
                assetsPlayer.seekTo(assetsPlayer.getCurrentPosition());
            } else if (assetsStopped) {
                assetsPlayer.prepare();
            }
            assetsPlayer.start();
            assetsStopped = false;
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
            assetsBtn.setEnabled(true);
        }
    }

    public void pauseAssets(View view) {
        if (!assetsPlayer.isPlaying()) {
            return;
        }
        assetsPlayer.pause();
        assetsStopped = false;
        assetsBtn.setEnabled(true);
    }

    public void stopAssets(View view) {
        // if (!assetsPlayer.isPlaying()) {
        //     return;
        // }
        assetsPlayer.stop();
        assetsStopped = true;
        assetsBtn.setEnabled(true);
    }

    public void playStorage(View view) {
        storageBtn.setEnabled(false);
        try {
            if (storagePlayer.isPlaying()) {
                storagePlayer.seekTo(storagePlayer.getCurrentPosition());
            } else if (storageStopped) {
                storagePlayer.prepare();
            }
            storagePlayer.start();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
            storageBtn.setEnabled(true);
        }
    }

    public void pauseStorage(View view) {
        if (storagePlayer.getCurrentPosition() == 0) {
            return;
        }
        storagePlayer.pause();
        storageStopped = false;
        storageBtn.setEnabled(true);
    }

    public void stopStorage(View view) {
        storagePlayer.stop();
        storageBtn.setEnabled(true);
    }

    public void playInternet(View view) {
        // new Thread(() -> {
        internetBtn.setEnabled(false);
        try {
            if (internetPlayer.isPlaying()) {
                internetPlayer.seekTo(internetPlayer.getCurrentPosition());
            } else if (internetStopped) {
                internetPlayer.prepare();
            }
            internetPlayer.start();
            internetStopped = false;
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
            internetBtn.setEnabled(true);
        }
        // }).start();
    }

    public void pauseInternet(View view) {
        if (!internetPlayer.isPlaying()) {
            return;
        }
        internetPlayer.pause();
        internetBtn.setEnabled(true);
    }

    public void stopInternet(View view) {
        // if (!internetPlayer.isPlaying()) {
        //     return;
        // }
        internetPlayer.stop();
        internetStopped = true;
        internetBtn.setEnabled(true);
    }
}