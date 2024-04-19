package com.example.sample.media;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PhoneLoopBackActivity extends Activity {

    private static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String RECORD = Manifest.permission.RECORD_AUDIO;
    private static final String AUDIO_SETTINGS = Manifest.permission.MODIFY_AUDIO_SETTINGS;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(READ, WRITE, RECORD));

    private static final int RATE = 8000;
    private static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    private static final int CHANNEL_OUT = AudioFormat.CHANNEL_OUT_MONO;
    private static final int BITS_PER_SAMPLE = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord mAudioRecord;
    private int mRecordInitCount = 0;
    private int mRecordBufferSize;

    private AudioTrack mAudioTrack;
    private int audioSessionId = -1;
    private boolean mRunning = true;
    private boolean mStopped = false;
    private byte[] mBuffer;
    private byte[] mRecordBuffer = new byte[0];
    private int count = 2;

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
            } else {
                new Thread(() -> {
                    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    // audioTrack 的 streamType 为 STREAM_VOICE_CALL 时，true 表示声音输出为扬声器，false 表示声音输出为听筒
                    audioManager.setSpeakerphoneOn(false);

                    initAudioRecord();
                    // initAEC();
                    initAGC();
                    initNS();
                    initAudioTrack();

                    mAudioRecord.startRecording();
                    // mAudioTrack.setPlaybackRate(RATE);
                    mAudioTrack.play();

                    while (!mStopped) {
                        if (!mRunning) {
                            SystemClock.sleep(200);
                            continue;
                        }
                        int readSize = mAudioRecord.read(mBuffer, 0, mRecordBufferSize);
                        if (readSize == AudioRecord.ERROR_INVALID_OPERATION || readSize == AudioRecord.ERROR_BAD_VALUE) {
                            continue;
                        }

                        // 1
                        // mAudioTrack.write(mBuffer, 0, readSize);

                        // 2
                        if (readSize > 0) {
                            int pcmEncoding = 16;
                            boolean mute = isMute(mBuffer, pcmEncoding);
                            XLog.d("MUTE: [%s]", mute);
                            if (mute) {
                                count--;
                                if (count <= 0) {
                                    count = 2;
                                    byte[] tempBytes = mRecordBuffer;
                                    if (tempBytes.length > 0) {
                                        mAudioTrack.write(tempBytes, 0, tempBytes.length);
                                    }
                                    mRecordBuffer = new byte[0];
                                }
                            } else {
                                count = 2;
                                mRecordBuffer = arrayCopy(mRecordBuffer, mBuffer);
                            }
                        }
                    }
                }).start();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_loop_back);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestPermissions(new String[]{READ, WRITE, RECORD, AUDIO_SETTINGS}, 0x111);
    }

    private byte[] arrayCopy(byte[] b1, byte[] b2) {
        byte[] temp = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, temp, 0, b1.length);
        System.arraycopy(b2, 0, temp, b1.length, b2.length);
        return temp;
    }

    private boolean isMute(byte[] bytes, int pcmEncoding) {
        double sMax = Math.pow(2, pcmEncoding);
        int bSize = pcmEncoding / 8;
        double mute = Math.pow(20, bSize) * bSize;
        List<Double> meanArr = new ArrayList<>();
        double[] bPowArr = new double[bSize];
        for (int j = 0; j < bSize; j++) {
            bPowArr[j] = Math.pow(256, j);
        }
        for (int i = 0, n = bytes.length / bSize; i < n; i++) {
            int s = i * bSize;
            double k = 0;
            for (int j = 0; j < bSize; j++) {
                int id = s + j;
                int b = bytes[id] & 0xFF;
                k += b * (j > 0 ? bPowArr[j] : 1);
                if (j == bSize - 1) {
                    if (k >= sMax / 2) k = (sMax - k);
                }
            }
            meanArr.add(k);
        }
        double arraySum = 0;
        for (int i = 0; i < meanArr.size(); i++) {
            arraySum += meanArr.get(i);
        }
        double mean = arraySum / meanArr.size();
        XLog.d(" MEAN: [%s], MUTE: [%s] ", mean, mute);
        return mean <= mute;
    }

    public void initAudioRecord() {
        mRecordBufferSize = AudioRecord.getMinBufferSize(RATE, CHANNEL_IN, BITS_PER_SAMPLE);
        mBuffer = new byte[mRecordBufferSize];
        // 注意，使用 VOICE_COMMUNICATION 能自动使用回声消除
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION, RATE, CHANNEL_IN,
                BITS_PER_SAMPLE, mRecordBufferSize);
        audioSessionId = mAudioRecord.getAudioSessionId();
        while (audioSessionId == 0 && ++mRecordInitCount < 5) {
            SystemClock.sleep(500);
            XLog.d("INIT_AUDIO_RECORD: [%s]-TH TIME", mRecordInitCount + 1);
            initAudioRecord();
        }
        if (audioSessionId == 0) {
            XLog.i("AUDIO_RECORD_INITIAL_FAIL");
        } else {
            XLog.i("AUDIO_RECORD_INITIALED, AUDIO_SESSION_ID: [%d]", audioSessionId);
        }
    }

    private void initAudioTrack() {
        // int streamType = AudioManager.STREAM_MUSIC;
        int mode = AudioTrack.MODE_STREAM;
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        AudioFormat audioFormat = new AudioFormat.Builder().setSampleRate(BITS_PER_SAMPLE).build();
        if (audioSessionId == -1) {
            mAudioTrack = new AudioTrack(audioAttributes, audioFormat, mRecordBufferSize, mode, AudioManager.AUDIO_SESSION_ID_GENERATE);
            // mAudioTrack = new AudioTrack(streamType, RATE, CHANNEL_OUT, BITS_PER_SAMPLE, mRecordBufferSize, mode);
        } else {
            mAudioTrack = new AudioTrack(audioAttributes, audioFormat, mRecordBufferSize, mode, audioSessionId);
            // mAudioTrack = new AudioTrack(streamType, RATE, CHANNEL_OUT, BITS_PER_SAMPLE, mRecordBufferSize, mode, audioSessionId);
        }
        XLog.i("AUDIO_TRACK_INITIALED");
    }

    private void initAEC() {
        if (AcousticEchoCanceler.isAvailable()) {
            AcousticEchoCanceler acousticEchoCanceler = AcousticEchoCanceler.create(audioSessionId);
            // 需要做非空判断，有可能为空
            if (acousticEchoCanceler != null) {
                acousticEchoCanceler.setEnabled(true);
                XLog.i("AEC_ENABLED");
            } else {
                XLog.i("AEC_CREATE_FAILED");
            }
        }
    }

    public void initAGC() {
        if (AutomaticGainControl.isAvailable()) {
            AutomaticGainControl automaticGainControl = AutomaticGainControl.create(audioSessionId);
            // 需要做非空判断，有可能为空
            if (automaticGainControl != null) {
                automaticGainControl.setEnabled(true);
                XLog.i("AGC_ENABLED");
            } else {
                XLog.i("AGC_CREATE_FAILED");
            }
        }
    }

    public void initNS() {
        if (NoiseSuppressor.isAvailable()) {
            NoiseSuppressor noiseSuppressor = NoiseSuppressor.create(audioSessionId);
            // 需要做非空判断，有可能为空
            if (noiseSuppressor != null) {
                noiseSuppressor.setEnabled(true);
                XLog.i("NS_ENABLED");
            } else {
                XLog.i("NS_CREATE_FAILED");
            }
        }
    }

    @Override
    protected void onResume() {
        mRunning = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRunning = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mStopped = true;
        if (mAudioRecord != null) {
            mAudioRecord.release();
            mAudioRecord = null;
        }
        if (mAudioTrack != null) {
            mAudioTrack.release();
            mAudioTrack = null;
        }
        super.onDestroy();
    }
}