package com.example.sample.media;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AudioTrackActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String RECORD = Manifest.permission.RECORD_AUDIO;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(READ, WRITE, RECORD));
    private Button mPlayBtn;
    private static final int RATE = 4000;
    private static final int CHANNEL = AudioFormat.CHANNEL_OUT_MONO;
    private static final int BITS_PER_SAMPLE = AudioFormat.ENCODING_PCM_16BIT;
    private byte[] mBuf;
    private AudioTrack mAudioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_track);

        requestPermissions(new String[]{READ, WRITE, RECORD}, 0x111);

        mPlayBtn = findViewById(R.id.play);
        mPlayBtn.setOnClickListener(this);
        mBuf = new byte[AudioTrack.getMinBufferSize(RATE, CHANNEL, BITS_PER_SAMPLE)];
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (0x111 == requestCode) {
            Integer[] grants = new Integer[grantResults.length];
            for (int i = 0; i < grantResults.length; ++i) {
                grants[i] = grantResults[i];
            }
            if (!Arrays.asList(permissions).containsAll(this.permissions) || Arrays.asList(grants).contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, RATE, CHANNEL, BITS_PER_SAMPLE, mBuf.length, AudioTrack.MODE_STREAM);
        mAudioTrack.play();
        mPlayBtn.setEnabled(false);
        try {
            FileInputStream inputStream = new FileInputStream(new File(getExternalCacheDir(), "/20201220_210942.pcm"));
            int len;
            while ((len = inputStream.read(mBuf)) != -1) {
                mAudioTrack.write(mBuf, 0, len);
            }
            mAudioTrack.stop();
            mAudioTrack.release();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
        mPlayBtn.setEnabled(true);
    }
}