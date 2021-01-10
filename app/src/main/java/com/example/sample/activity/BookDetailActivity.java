package com.example.sample.activity;

import android.os.Bundle;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        if (savedInstanceState == null) {
            SimpleFragmentActivity.BookDetailFragment bookDetailFragment = new SimpleFragmentActivity.BookDetailFragment();
            bookDetailFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.book_detail, bookDetailFragment).commit();
        }
    }
}