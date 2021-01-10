package com.example.sample.media;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class CameraV2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String CAMERA = Manifest.permission.CAMERA;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(READ, WRITE, CAMERA));
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int REQUEST_CODE = 1;

    private AutoFitTextureView mTextureView;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private String mCameraId;
    private Size mPreviewSize;
    private ImageReader mImageReader;
    private CameraCaptureSession mCaptureSession;
    private CaptureRequest.Builder mPreviewBuilder;
    private CaptureRequest mPreviewRequest;
    private Handler mHandler;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 有时没有效果？试下 getSupportActionBar().hide();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_v2);
        getSupportActionBar().hide();
        requestPermissions(new String[]{READ, WRITE, CAMERA}, REQUEST_CODE);

        HandlerThread cameraBackgroundThread = new HandlerThread("CameraBackgroundThread");
        cameraBackgroundThread.start();
        mHandler = new Handler(cameraBackgroundThread.getLooper());

        mTextureView = findViewById(R.id.surface);
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                openCamera(width, height);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                configureTransform(width, height);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        findViewById(R.id.capture).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
            Integer[] grants = new Integer[grantResults.length];
            for (int i = 0; i < grantResults.length; ++i) {
                grants[i] = grantResults[i];
            }
            if (!Arrays.asList(permissions).containsAll(this.permissions) ||
                    Arrays.asList(grants).contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void openCamera(int width, int height) {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mCameraId = findBackCamera();
        setUpOutputs(width, height);
        configureTransform(width, height);
        try {
            CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    CameraV2Activity.this.mCameraDevice = camera;
                    createPreviewSession();
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    camera.close();
                    CameraV2Activity.this.mCameraDevice = null;
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    camera.close();
                    CameraV2Activity.this.mCameraDevice = null;
                    CameraV2Activity.this.finish();
                }
            };

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (mCameraId == null) {
                return;
            }
            mCameraManager.openCamera(mCameraId, stateCallback, mHandler);
        } catch (CameraAccessException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }

    // 根据手机的旋转方向确定预览图像的方向
    private void configureTransform(int viewWidth, int viewHeight) {
        if (null == mPreviewSize) {
            return;
        }
        // 获取手机的旋转方向
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        // 处理手机横屏的情况
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        // 处理手机倒置的情况
        else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }

    private void setUpOutputs(int width, int height) {
        try {
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            StreamConfigurationMap configurationMap =
                    characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size max = Collections.max(Arrays.asList(configurationMap.getOutputSizes(ImageFormat.JPEG)), (o1, o2) ->
                    Long.signum((long) o1.getWidth() * o1.getHeight() - (long) o2.getWidth() * o2.getHeight()));
            mImageReader = ImageReader.newInstance(max.getWidth(), max.getHeight(), ImageFormat.JPEG, 2);
            mImageReader.setOnImageAvailableListener(reader -> {
                ByteBuffer buffer = reader.acquireNextImage().getPlanes()[0].getBuffer();
                byte[] buf = new byte[buffer.remaining()];
                try {
                    File file = new File(getExternalCacheDir(),
                            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpeg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    buffer.get(buf);
                    fileOutputStream.write(buf, 0, buf.length);
                    fileOutputStream.close();
                    Toast.makeText(CameraV2Activity.this, file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    XLog.d("FILE_PATH: [%s]", file.getAbsolutePath());
                } catch (Exception e) {
                    ErrorPrintUtil.printErrorMsg(e);
                }
            }, mHandler);

            mPreviewSize = chooseOptimalSize(configurationMap.getOutputSizes(SurfaceTexture.class), width, height, max);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTextureView.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            } else {
                mTextureView.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            }
        } catch (CameraAccessException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }

    private Size chooseOptimalSize(Size[] outputSizes, int width, int height, Size max) {
        int w = max.getWidth();
        int h = max.getHeight();
        List<Size> list = new ArrayList<>();
        for (Size size : outputSizes) {
            if (size.getWidth() / size.getHeight() == w / h && size.getWidth() >= width && size.getHeight() >= height) {
                list.add(size);
            }
        }
        if (list.size() > 0) {
            Size size = Collections.max(list, (o1, o2) -> Long.signum((long) o1.getWidth() * o1.getHeight() - (long) o2.getWidth() * o2.getHeight()));
            XLog.d("PREVIEW_SIZE: [%s] * [%s]", size.getWidth(), size.getHeight());
            return size;
        }
        XLog.d("NON MATCHABLE SIZE FOUND");
        return outputSizes[0];
    }

    private void createPreviewSession() {
        SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        try {
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            Surface surface = new Surface(surfaceTexture);
            mPreviewBuilder.addTarget(surface);

            mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (mCameraDevice == null) {
                        return;
                    }

                    mCaptureSession = session;
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    mPreviewRequest = mPreviewBuilder.build();
                    try {
                        mCaptureSession.setRepeatingRequest(mPreviewRequest, null, mHandler);
                    } catch (CameraAccessException e) {
                        ErrorPrintUtil.printErrorMsg(e);
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(CameraV2Activity.this, "配置失败", Toast.LENGTH_LONG).show();
                }
            }, mHandler);

        } catch (CameraAccessException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }

    private String findBackCamera() {
        try {
            String[] cameraIdList = mCameraManager.getCameraIdList();
            for (String cameraId : cameraIdList) {
                if (CameraCharacteristics.LENS_FACING_BACK ==
                        mCameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING)) {
                    return cameraId;
                }
            }
            XLog.e("NON BACK CAMERA FOUND");
        } catch (CameraAccessException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        captureStillPicture();
    }

    private void captureStillPicture() {
        if (mCameraDevice == null) {
            return;
        }

        try {
            CaptureRequest.Builder captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(mImageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO);
            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            XLog.d("ROTATION: [%s]", rotation);
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            mCaptureSession.stopRepeating();
            mCaptureSession.capture(captureBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO);
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    try {
                        mCaptureSession.setRepeatingRequest(mPreviewRequest, null, mHandler);
                    } catch (CameraAccessException e) {
                        ErrorPrintUtil.printErrorMsg(e);
                    }
                }
            }, mHandler);

        } catch (CameraAccessException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }
}