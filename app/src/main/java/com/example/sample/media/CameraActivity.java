package com.example.sample.media;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.File;
import java.io.FileOutputStream;
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

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final int AUTO_FOCUS = 0x31;
    private static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String CAMERA = Manifest.permission.CAMERA;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(READ, WRITE, CAMERA));
    private Button captureBtn;
    private SurfaceHolder surfaceHolder;
    private int screenWidth;
    private int screenHeight;
    private Camera camera;
    private boolean isPreview = false;
    private Handler handler;
    private boolean stopTimerTask = false;
    private boolean isBackCamera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 有时没有效果？试下 getSupportActionBar().hide();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        getSupportActionBar().hide();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            XLog.i("NO_CAMERA_FEATURE");
            return;
        }

        requestPermission();

        // isBackCamera = false;
        captureBtn = findViewById(R.id.capture);
        captureBtn.setEnabled(false);
        captureBtn.setOnClickListener(this);

        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        XLog.i("SCREEN_WIDTH: [%d], SCREEN_HEIGHT: [%d]", screenWidth, screenHeight);

        SurfaceView surfaceView = findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (camera == null) {
                    return;
                }
                if (isPreview) {
                    camera.stopPreview();
                }
                camera.release();
                camera = null;
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == AUTO_FOCUS) {
                    if (camera != null) {
                        camera.autoFocus(null);
                        // 处理消息后，2 秒后再发送该消息，那么相当于每隔 2 秒 发送一次该消息，从而每隔 2 秒自动对焦一次
                        handler.sendEmptyMessageDelayed(AUTO_FOCUS, 2000);
                    }
                }
            }
        };

    }

    private void requestPermission() {
        // 好像是 Android 6.0 以后必须动态申请权限
        // 也可以直接在手机上应用管理中，给该应用设置需要的这几项权限策略为 ‘总是允许’ 或 ‘在使用时允许’，就不需要下面的动态申请逻辑
        int checked = checkSelfPermission(WRITE);
        if (checked != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{READ, WRITE}, REQUEST_CODE);
        }
        checked = checkSelfPermission(CAMERA);
        if (checked != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{CAMERA}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
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

    private void initCamera() {
        if (isPreview) {
            return;
        }

        if (camera != null) {
            camera.release();
            camera = null;
        }

        if (isBackCamera) {
            camera = Camera.open(findCamera(Camera.CameraInfo.CAMERA_FACING_BACK));
        } else {
            camera = Camera.open(findCamera(Camera.CameraInfo.CAMERA_FACING_FRONT));
        }
        camera.setDisplayOrientation(90);
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : sizes) {
            XLog.i("SUPPORTED_WIDTH: [%d], SUPPORTED_HEIGHT:  [%d]", size.width, size.height);
        }
        parameters.setPreviewSize(sizes.get(0).width, sizes.get(0).height);
        parameters.setPreviewFpsRange(4, 10);
        // 自动对焦似乎没有效果
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setJpegQuality(85);
        parameters.setRotation(90);
        parameters.setPictureSize(screenWidth, screenHeight);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            captureBtn.setEnabled(true);
            isPreview = true;
            // 每隔 2 秒自动对焦一次
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!stopTimerTask) {
                        handler.sendEmptyMessage(AUTO_FOCUS);
                    }
                }
            }, 0, 2000);
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(e);
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

    public void onClick(View view) {
        stopTimerTask = true;
        captureBtn.setEnabled(false);
        camera.autoFocus((success, camera) -> {
            if (!success) {
                return;
            }
            camera.takePicture(() -> {
                        // 快门事件处理逻辑
                    },
                    (data, camera12) -> {
                        // 相片元信息事件处理逻辑
                    },
                    (data, camera1) -> {
                        View saveView = getLayoutInflater().inflate(R.layout.save, null);
                        // String photoName = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now());
                        String photoName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        EditText photoNameEditText = saveView.findViewById(R.id.photo_name);
                        photoNameEditText.setText(photoName);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        bitmap = rotateBitmapByDegree(bitmap, isBackCamera ? 90 : 270);
                        ((ImageView) (saveView.findViewById(R.id.photo))).setImageBitmap(bitmap);
                        Bitmap finalBitmap = bitmap;
                        new AlertDialog.Builder(this).setView(saveView).setPositiveButton("保存", (dialog, which) -> {
                            try {
                                FileOutputStream outputStream = new FileOutputStream(new File(CameraActivity.this.getFilesDir(), photoNameEditText.getText().toString() + ".jpg"));
                                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                outputStream.close();
                            } catch (Exception e) {
                                ErrorPrintUtil.printErrorMsg(e);
                            }
                        }).setNegativeButton("取消", null).show();
                        camera1.stopPreview();
                        isPreview = false;
                        camera1.startPreview();
                        captureBtn.setEnabled(true);
                        isPreview = true;
                        stopTimerTask = false;
                    });
        });
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        if (degree == 0 || null == bitmap) {
            return bitmap;
        }
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap1 == null) {
            bitmap1 = bitmap;
        }
        if (bitmap1 != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmap1;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}