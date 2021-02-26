package com.example.sample.system_service;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TelephonyManagerActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony_manager);

        requestPermissions(new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 0x11);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        XLog.d("SIM_STATE: [%s]", telephonyManager.getSimState());
        XLog.d("CALL_STATE: [%s]", telephonyManager.getCallState());
        XLog.d("PHONE_TYPE: [%s]", telephonyManager.getPhoneType());
        XLog.d("PHONE_COUNT: [%s]", telephonyManager.getPhoneCount());
        XLog.d("CALL_STATE: [%s]", telephonyManager.getCallState());
        XLog.d("ANDROID_ID: [%s]",  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

    }
}