package com.example.sample.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.util.ErrorPrintUtil;

import androidx.annotation.Nullable;

public class SimpleService extends Service {

    @Override
    public void onCreate() {
        XLog.d("ON_CREATE");
        Toast.makeText(this, "SIMPLE_SERVICE_CUR_PID: " + android.os.Process.myPid(), Toast.LENGTH_LONG).show();
        XLog.i("SIMPLE_SERVICE_CUR_PID: " + android.os.Process.myPid());
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        XLog.d("ON_BIND");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        XLog.d("ON_UNBIND");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        XLog.d("ON_START_COMMAND");
        // try {
        //     // TimeUnit.SECONDS.sleep(20);
        //     // 会引起【程序停止响应】
        //     long end = System.currentTimeMillis() + 20 * 1000;
        //     while (System.currentTimeMillis() < end) {
        //         synchronized (this) {
        //             wait(end - System.currentTimeMillis());
        //         }
        //     }
        // } catch (InterruptedException e) {
        //     ErrorPrintUtil.printErrorMsg(e);
        // }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        XLog.d("ON_DESTROY");
        super.onDestroy();
    }
}
