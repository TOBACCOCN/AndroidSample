package com.example.sample.system_service;

import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import com.elvishew.xlog.XLog;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BatteryManagerActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        XLog.d("STATUS: [%d]", batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS));
        XLog.d("BATTERY_STATUS_CHARGING: [%d]", BatteryManager.BATTERY_STATUS_CHARGING);
        XLog.d("BATTERY_STATUS_DISCHARGING: [%d]", BatteryManager.BATTERY_STATUS_DISCHARGING);
        XLog.d("BATTERY_STATUS_FULL: [%d]", BatteryManager.BATTERY_STATUS_FULL);
        XLog.d("BATTERY_STATUS_NOT_CHARGING: [%d]", BatteryManager.BATTERY_STATUS_NOT_CHARGING);
        XLog.d("BATTERY_STATUS_UNKNOWN: [%d]", BatteryManager.BATTERY_STATUS_UNKNOWN);

        XLog.d("CAPACITY: [%d]", batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
        XLog.d("CURRENT_AVERAGE: [%d]", batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE));
        XLog.d("CURRENT_NOW: [%d]", batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW));
        XLog.d("CHARGE_COUNTER: [%d]", batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER));
        XLog.d("ENERGY_COUNTER: [%d]", batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER));
    }
}
