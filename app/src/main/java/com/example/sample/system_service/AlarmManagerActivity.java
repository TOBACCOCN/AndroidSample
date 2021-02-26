package com.example.sample.system_service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmManagerActivity extends AppCompatActivity {

    private AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void alarm(View view) {
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent operation = PendingIntent.getActivity(this, 0, intent, 0);
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, operation);
        // 显示闹铃设置成功的提示信息
        Toast.makeText(this, "闹铃设置成功啦",
                Toast.LENGTH_SHORT).show();
    }
}