package com.example.sample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class ViewFlipperActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flapper);

        viewFlipper = findViewById(R.id.flipper);
    }

    public void prev(View view) {
        viewFlipper.showPrevious();
        viewFlipper.stopFlipping();
    }

    public void next(View view) {
        viewFlipper.showNext();
        viewFlipper.stopFlipping();
    }

    public void auto(View view) {
        viewFlipper.startFlipping();
    }
}