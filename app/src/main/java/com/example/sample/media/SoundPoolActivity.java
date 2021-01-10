package com.example.sample.media;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class SoundPoolActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private int res1;
    private int res2;
    private int res3;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_pool);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        AudioAttributes attr = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(16).setAudioAttributes(attr).build();
        res1 = soundPool.load(this, R.raw.glass_zh, 1);
        res2 = soundPool.load(this, R.raw.glass_en, 1);
        res3 = soundPool.load(this, R.raw.glass_kr, 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                soundPool.play(res1, 1, 1, 0, 0, 1);
                break;
            case R.id.btn2:
                soundPool.play(res2, 1, 1, 0, 0, 1);
                break;
            case R.id.btn3:
                soundPool.play(res3, 1, 1, 0, 0, 1);
                break;
            default:
                break;
        }
    }
}