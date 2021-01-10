package com.example.sample.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class RatingBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);

        ImageView imageView = findViewById(R.id.picture);
        RatingBar seekBar = findViewById(R.id.rating);
        seekBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> imageView.setImageAlpha((int) rating * 51));
    }
}