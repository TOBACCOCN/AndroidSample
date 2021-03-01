package com.example.sample.drawable;

import android.os.Bundle;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class MeshBitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SimpleImageView(this,R.drawable.dufu));
    }

}