package com.example.sample.drawable;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

// frame animation
public class AnimationDrawableActivity extends AppCompatActivity implements View.OnClickListener {

    private Drawable mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);

        Button startBtn = findViewById(R.id.start);
        Button stopBtn = findViewById(R.id.stop);
        ImageView imageView = findViewById(R.id.image);
        mBackground = imageView.getBackground();
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (mBackground instanceof AnimationDrawable) {
                    ((AnimationDrawable)mBackground).start();
                }
                break;
            case R.id.stop:
                if (mBackground instanceof AnimationDrawable) {
                    ((AnimationDrawable)mBackground).stop();
                }
                break;
            default:
                break;
        }
    }
}