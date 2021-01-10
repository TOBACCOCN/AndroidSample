package com.example.sample.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.sample.R;
import com.google.android.material.snackbar.Snackbar;

public class ButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        RadioGroup radioGroup = findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String text = "您是" + (R.id.male == checkedId ? "男士" : "女士");
            Snackbar.make(radioGroup, text, Snackbar.LENGTH_LONG).setAction("action", null).show();
        });
    }
}