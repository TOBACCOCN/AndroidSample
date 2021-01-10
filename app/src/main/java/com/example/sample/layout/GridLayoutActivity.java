package com.example.sample.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.sample.R;

public class GridLayoutActivity extends AppCompatActivity {

    private String[] chars = new String[]{"7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "－",
            ".", "0", "=", "＋"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);

        GridLayout gridLayout = findViewById(R.id.root);

        for (int i = 0; i < chars.length; ++i) {
            Button button = new Button(this);
            button.setText(chars[i]);
            button.setTextSize(40);
            GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2);
            GridLayout.Spec colSpec = GridLayout.spec(i % 4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
            button.setGravity(Gravity.CENTER);
            gridLayout.addView(button, params);
        }
    }
}