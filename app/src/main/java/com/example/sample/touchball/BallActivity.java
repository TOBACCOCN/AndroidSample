package com.example.sample.touchball;

import android.os.Bundle;

import com.example.sample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);

        ConstraintLayout root = findViewById(R.id.ball);
        DrawView view = new DrawView(this);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
        root.addView(view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}