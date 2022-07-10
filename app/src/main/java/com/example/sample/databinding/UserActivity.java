package com.example.sample.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.Manifest;
import android.os.Bundle;

import com.example.sample.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserBinding activityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        // activityUserBinding.setUser(new User("法外狂徒张三", "https://bkimg.cdn.bcebos.com/pic/8b13632762d0f703918fa2c01ab1463d269759ee6fde"));
    }
}