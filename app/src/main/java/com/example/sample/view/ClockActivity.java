package com.example.sample.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.sample.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class ClockActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        chronometer = findViewById(R.id.chronometer);
        button = findViewById(R.id.btn);

        chronometer.setOnChronometerTickListener(chronometer -> {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() > 20 * 1000) {
                Snackbar.make(button, "20 秒已到", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                chronometer.stop();
                button.setEnabled(true);
            }
        });
    }

    public void doClick(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        button.setEnabled(false);
    }
}