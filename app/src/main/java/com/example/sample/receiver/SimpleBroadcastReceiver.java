package com.example.sample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;

public class SimpleBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.d("ON_RECEIVE");
        intent.getExtras().getString("key");
    }
}
