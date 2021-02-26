package com.example.sample.system_service;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class VibratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrator);

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // vibrator.vibrate(VibrationEffect.createOneShot(2000, 255));
        // vibrator.vibrate(VibrationEffect.createWaveform(new long[]{1000, 2000, 3000}, 1));
        vibrator.vibrate(VibrationEffect.createWaveform(new long[]{1000, 3000, 5000}, new int[]{80, 160, 240}, -1));
        Toast.makeText(this, "VIBRATING", Toast.LENGTH_LONG).show();
    }
}