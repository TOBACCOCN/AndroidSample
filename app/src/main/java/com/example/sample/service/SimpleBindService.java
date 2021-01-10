package com.example.sample.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.elvishew.xlog.XLog;

import androidx.annotation.Nullable;

public class SimpleBindService extends Service {

    private IBinder iBinder = new SimpleBinder();

    public class SimpleBinder extends Binder {
        public String getServicePackageName() {
            return SimpleBindService.this.getPackageName();
        }
    }

    @Override
    public void onCreate() {
        XLog.d("ON_CREATE");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        XLog.d("ON_BIND");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        XLog.d("ON_UNBIND");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        XLog.d("ON_REBIND");
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        XLog.d("ON_START_COMMAND");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        XLog.d("ON_DESTROY");
        super.onDestroy();
    }
}
