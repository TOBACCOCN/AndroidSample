package com.example.sample.service;

import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.example.sample.util.ErrorPrintUtil;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

/**
 * An {@link JobIntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class SimpleIntentService extends JobIntentService {

    public SimpleIntentService() {
        super();
    }

    @Override
    public void onCreate() {
        XLog.i("ON_CREATE");
        super.onCreate();
    }

    // @Override
    // protected void onHandleIntent(Intent intent) {
    //     XLog.i("ON_HANDLE_INTENT");
    //     if (intent != null) {
    //
    //         try {
    //             // TimeUnit.SECONDS.sleep(20);
    //             // 不会引起【程序停止响应】
    //             long end = System.currentTimeMillis() + 20 * 1000;
    //             while (System.currentTimeMillis() < end) {
    //                 synchronized (this) {
    //                     wait(end - System.currentTimeMillis());
    //                 }
    //             }
    //         } catch (InterruptedException e) {
    //             ErrorPrintUtil.printErrorMsg(e);
    //         }
    //     }
    // }

    @Override
    public void onDestroy() {
        XLog.i("ON_DESTROY");
        super.onDestroy();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        XLog.i("ON_HANDLE_INTENT");

        try {
            // TimeUnit.SECONDS.sleep(20);
            // 不会引起【程序停止响应】
            long end = System.currentTimeMillis() + 20 * 1000;
            while (System.currentTimeMillis() < end) {
                synchronized (this) {
                    wait(end - System.currentTimeMillis());
                }
            }
        } catch (InterruptedException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }
}
