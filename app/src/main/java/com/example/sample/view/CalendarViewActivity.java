package com.example.sample.view;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        CalendarView calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Toast.makeText(CalendarViewActivity.this, year + "年" + month + "月" + dayOfMonth + "日", Toast.LENGTH_LONG).show();
        });
    }
}