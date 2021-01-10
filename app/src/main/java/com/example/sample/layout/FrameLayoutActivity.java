package com.example.sample.layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.example.sample.R;

import java.util.Timer;
import java.util.TimerTask;

public class FrameLayoutActivity extends AppCompatActivity {

    private final int[] colors = new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6};
    private final int[] ids = new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6};
    private final TextView[] textViews = new TextView[ids.length];
    private int currentColor = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 123) {
                    for (int i = 0; i < textViews.length; ++i) {
                        textViews[i].setBackgroundResource(colors[(currentColor + i) % colors.length]);
                    }
                    ++currentColor;
                }
            }
        };

        for (int i = 0; i < ids.length; ++i) {
            textViews[i] = findViewById(ids[i]);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(123);
            }
        }, 0, 200);
    }
}