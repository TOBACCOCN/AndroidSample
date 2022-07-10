package com.example.sample.resources;

import android.annotation.SuppressLint;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sample.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ResourcesDrawableActivity extends AppCompatActivity {

    private ImageView mAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        ImageView imageView = findViewById(R.id.image);
        imageView.setImageResource(R.drawable.ic_launcher);
        ImageView clipView = findViewById(R.id.clip);
        ClipDrawable drawable = (ClipDrawable) clipView.getDrawable();
        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (drawable.getLevel() < 10000) {
                    drawable.setLevel(drawable.getLevel() + 100);
                    sendEmptyMessageDelayed(0x11, 200);
                }
            }
        };
        handler.sendEmptyMessage(0x11);

        mAnimView = findViewById(R.id.anim);
    }

    public void startAnim(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_translate);
        animation.setFillAfter(true);
        mAnimView.startAnimation(animation);
    }
}