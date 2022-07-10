package com.example.sample.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import androidx.annotation.NonNull;

public class ProgressBarActivity extends Activity {

    private static Handler handler;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        ProgressBar progressBar = findViewById(R.id.bar);
        ProgressBar progressBar2 = findViewById(R.id.bar2);

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 111) {
                    XLog.i("status: [%d]", status);
                    progressBar.setProgress(status);
                    progressBar2.setProgress(status);
                }
            }
        };

        new Thread(() -> {
            while (status < 100) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    ErrorPrintUtil.printErrorMsg(e);
                }
                status = (int) (Math.random() * 100);
                handler.sendEmptyMessage(111);
            }
        }).start();
    }

}