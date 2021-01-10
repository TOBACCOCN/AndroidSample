package com.example.sample.media;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AudioRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String RECORD = Manifest.permission.RECORD_AUDIO;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(READ, WRITE, RECORD));
    private static final int RATE = 4000;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int BITS_PER_SAMPLE = AudioFormat.ENCODING_PCM_16BIT;
    private Button mRecordBtn;
    private Button mStopBtn;
    private AudioRecord mAudioRecord;
    private byte[] mBuf;
    private boolean mStopped = true;
    private String mFilePath;
    private FileOutputStream mOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);

        requestPermissions(new String[]{READ, WRITE, RECORD}, 0x111);

        mRecordBtn = findViewById(R.id.record);
        mStopBtn = findViewById(R.id.stop);
        mRecordBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mStopBtn.setEnabled(false);

        int minBufferSize = AudioRecord.getMinBufferSize(RATE, CHANNEL, BITS_PER_SAMPLE);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, RATE, CHANNEL, BITS_PER_SAMPLE, minBufferSize);
        mBuf = new byte[minBufferSize];

        new Thread(() -> {
            while(true) {
                try {
                    if (mStopped) {
                        SystemClock.sleep(200);
                        continue;
                    }
                    mAudioRecord.read(mBuf, 0, mBuf.length);
                    mOutputStream.write(mBuf, 0, mBuf.length);
                } catch (Exception e) {
                    ErrorPrintUtil.printErrorMsg(e);
                }
            }
        }).start();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record:
                mAudioRecord.startRecording();
                mRecordBtn.setEnabled(false);
                mStopBtn.setEnabled(true);
                mStopped = false;
                mFilePath = getExternalCacheDir() + File.separator + DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now());
                XLog.i("mFilePath: [%s]", mFilePath);
                try {
                    mOutputStream = new FileOutputStream(mFilePath + ".pcm");
                } catch (Exception e) {
                    ErrorPrintUtil.printErrorMsg(e);
                    mRecordBtn.setEnabled(true);
                    mStopBtn.setEnabled(false);
                }
                break;
            case R.id.stop:
                mStopped = true;
                try {
                    mAudioRecord.stop();
                    mRecordBtn.setEnabled(true);
                    mStopBtn.setEnabled(false);
                    mOutputStream.flush();
                    mOutputStream.close();
                    AudioUtil.pcm2Wav(mFilePath + ".pcm", mFilePath + ".wav", getChannel(CHANNEL), RATE, getBitsPerSample(BITS_PER_SAMPLE));
                } catch (Exception e) {
                    ErrorPrintUtil.printErrorMsg(e);
                    mRecordBtn.setEnabled(false);
                    mStopBtn.setEnabled(true);
                }
                break;
            default:
                break;
        }
    }

    private short getBitsPerSample(int bitsPerSample) {
        switch(bitsPerSample) {
            case AudioFormat.ENCODING_PCM_16BIT:
                return 16;
            case AudioFormat.ENCODING_PCM_8BIT:
                return 8;
        }
        return 0;
    }

    private short getChannel(int channel) {
        switch(channel) {
            case AudioFormat.CHANNEL_IN_MONO:
                return 1;
            case AudioFormat.CHANNEL_IN_STEREO:
                return 2;
        }
        return 0;
    }
}