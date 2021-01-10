package com.example.sample.view;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class NumberPickerActivity extends AppCompatActivity {
    private int min = 30;
    private int max = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_picker);


        NumberPicker picker1 = findViewById(R.id.picker1);
        picker1.setMinValue(10);
        picker1.setMaxValue(50);
        picker1.setValue(min);

        NumberPicker picker2 = findViewById(R.id.picker2);
        picker2.setMinValue(100);
        picker2.setMaxValue(200);
        picker2.setValue(max);

        picker1.setOnValueChangedListener((picker, oldVal, newVal) -> {
            min = newVal;
            Toast.makeText(this, "最低价：" + min + "，最高价：" + max, Toast.LENGTH_LONG).show();
        });
        picker2.setOnValueChangedListener((picker, oldVal, newVal) -> {
            max = newVal;
            Toast.makeText(this, "最低价：" + min + "，最高价" + max, Toast.LENGTH_LONG).show();
        });
    }
}