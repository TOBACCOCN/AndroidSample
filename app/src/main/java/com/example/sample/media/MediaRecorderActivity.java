package com.example.sample.media;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MediaRecorderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final int AUTO_FOCUS = 0x22;
    private static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String RECORD = Manifest.permission.RECORD_AUDIO;
    private static final String CAMERA = Manifest.permission.CAMERA;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(READ, WRITE, RECORD, CAMERA));
    private Button recordAudioBtn;
    private Button stopAudioBtn;
    private Button recordVideoBtn;
    private Button stopVideoBtn;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder holder;
    private Camera camera;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_media_recorder);

        getSupportActionBar().hide();

        // 好像是 Android 6.0 以后必须动态申请权限
        // 也可以直接在手机上应用管理中，给该应用设置需要的这几项权限策略为 ‘总是允许’ 或 ‘在使用时允许’，就不需要下面的动态申请逻辑
        int checked = checkSelfPermission(WRITE);
        if (checked != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{READ, WRITE}, REQUEST_CODE);
        }
        checked = checkSelfPermission(RECORD);
        if (checked != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{RECORD}, REQUEST_CODE);
        }
        checked = checkSelfPermission(CAMERA);
        if (checked != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{CAMERA}, REQUEST_CODE);
        }

        recordAudioBtn = findViewById(R.id.recordAudio);
        stopAudioBtn = findViewById(R.id.stopAudio);
        recordVideoBtn = findViewById(R.id.recordVideo);
        stopVideoBtn = findViewById(R.id.stopVideo);
        stopAudioBtn.setEnabled(false);
        stopVideoBtn.setEnabled(false);
        recordAudioBtn.setOnClickListener(this);
        stopAudioBtn.setOnClickListener(this);
        recordVideoBtn.setOnClickListener(this);
        stopVideoBtn.setOnClickListener(this);

        SurfaceView surfaceView = findViewById(R.id.surface);
        holder = surfaceView.getHolder();
        holder.setFixedSize(1280, 720);

        handler =new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == AUTO_FOCUS) {
                    camera.autoFocus(null);
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
            if (!Arrays.asList(permissions).containsAll(this.permissions) || Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recordAudio:
                recordAudioBtn.setEnabled(false);
                mediaRecorder = new MediaRecorder();
                // 以下顺序不可更换
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    File file = new File(getFilesDir(), new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".amr");
                    XLog.i("FILE: " + file);
                    // 直接 mediaRecorder.setOutputFile(file); 报错
                    mediaRecorder.setOutputFile(file.getAbsolutePath());
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    stopAudioBtn.setEnabled(true);
                } catch (IOException e) {
                    ErrorPrintUtil.printErrorMsg(e);
                    recordAudioBtn.setEnabled(true);
                }
                break;
            case R.id.stopAudio:
                stopAudioBtn.setEnabled(false);
                mediaRecorder.stop();
                mediaRecorder.release();
                recordAudioBtn.setEnabled(true);
                break;
            case R.id.recordVideo:
                recordVideoBtn.setEnabled(false);
                mediaRecorder = new MediaRecorder();
                camera = Camera.open(findCamera(Camera.CameraInfo.CAMERA_FACING_BACK));
                camera.setDisplayOrientation(90);
                camera.unlock();
                mediaRecorder.setCamera(camera);
                // mediaRecorder.setOrientationHint(90);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                mediaRecorder.setVideoFrameRate(30);
                mediaRecorder.setVideoSize(1280, 720);
                File file = new File(getFilesDir(), new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4");
                mediaRecorder.setOutputFile(file.getAbsolutePath());
                mediaRecorder.setPreviewDisplay(holder.getSurface());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(AUTO_FOCUS);
                    }
                }, 0, 2000);
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    stopVideoBtn.setEnabled(true);
                } catch (IOException e) {
                    ErrorPrintUtil.printErrorMsg(e);
                    recordVideoBtn.setEnabled(true);
                    stopVideoBtn.setEnabled(false);
                }
                break;
            case R.id.stopVideo:
                stopVideoBtn.setEnabled(false);
                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
                recordVideoBtn.setEnabled(true);
                break;
            default:
                break;
        }
    }

    private int findCamera(int frontOrBack) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();

        for (int camIdx = 0; camIdx < cameraCount; ++camIdx) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == frontOrBack) {
                return camIdx;
            }
        }
        return -1;
    }
}