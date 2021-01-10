package com.example.sample.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.sample.R;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class DateTimePickerActivity extends AppCompatActivity {

    private Calendar calendar;
    private EditText editText;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);

        editText = findViewById(R.id.edit);
        DatePicker datePicker = findViewById(R.id.date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        showDateTime(year, month + 1, day, hour, minute);

        datePicker.init(year, month, day, (view, year1, monthOfYear, dayOfMonth) -> {
            DateTimePickerActivity.this.year = year1;
            DateTimePickerActivity.this.month = monthOfYear + 1;
            DateTimePickerActivity.this.day = dayOfMonth;
            showDateTime(year, month, day, hour, minute);
        });

        TimePicker timePicker = findViewById(R.id.time);
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            DateTimePickerActivity.this.hour = hourOfDay;
            DateTimePickerActivity.this.minute = minute;
            showDateTime(year, month, day, hour, minute);
        });
    }

    private void showDateTime(int year, int month, int day, int hour, int minute) {
        editText.setText("购书时间是：" + year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分");
    }

    public void chooseDate(View view) {
        new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            DateTimePickerActivity.this.year = year;
            DateTimePickerActivity.this.month = month + 1;
            DateTimePickerActivity.this.day = dayOfMonth;
            showDateTime(year, month + 1, day, hour, minute);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void chooseTime(View view) {
        new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
            DateTimePickerActivity.this.hour = hourOfDay;
            DateTimePickerActivity.this.minute = minute;
            showDateTime(year, month, day, hour, minute);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }
}