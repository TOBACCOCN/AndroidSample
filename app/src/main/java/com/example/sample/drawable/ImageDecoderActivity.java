package com.example.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ImageDecoderActivity extends AppCompatActivity {

    private Handler mHandler;
    private Drawable mDrawable;

    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_decoder);

        ImageView imageView = findViewById(R.id.image);
        mHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast.makeText(ImageDecoderActivity.this, "图片解析完成", Toast.LENGTH_LONG).show();
                imageView.setImageDrawable(mDrawable);
                if (mDrawable instanceof AnimatedImageDrawable) {
                    ((AnimatedImageDrawable)mDrawable).start();
                }
            }
        };

        ImageDecoder.Source source = ImageDecoder.createSource(getResources(), R.drawable.panda);
        new Thread(() -> {
            try {
                mDrawable = ImageDecoder.decodeDrawable(source, (decoder, info, src) -> {
                    mHandler.sendEmptyMessage(0x11);
                });
            } catch (IOException e) {
                ErrorPrintUtil.printErrorMsg(e);
            }
        }).start();
    }
}