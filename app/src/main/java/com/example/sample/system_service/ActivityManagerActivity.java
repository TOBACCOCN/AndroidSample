package com.example.sample.system_service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.io.BufferedReader;
import java.io.FileReader;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMem = memoryInfo.totalMem;
        long availMem = memoryInfo.availMem;
        long threshold = memoryInfo.threshold;
        XLog.i("TOTAL_MEM:[%s],  AVAIL_MEM: [%s], THRESHOLD: [%s]",
                formatSize(totalMem), formatSize(availMem), formatSize(threshold));

        XLog.i("TOTAL_MEM:[%s]", formatSize(getTotalRam()));
        // MemInfoReader memReader = new MemInfoReader();
        // memReader.readMemInfo();
    }

    public long getTotalRam() {
        String path = "/proc/meminfo";
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            long totalRam = Long.parseLong(br.readLine().split(":")[1].trim().split(" ")[0]);
            br.close();
            return totalRam * 1024;
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
            return 0;
        }
    }

    private String formatSize(long bytes) {
        return formatSize(bytes, false);
    }

    @SuppressLint("DefaultLocale")
    private String formatSize(long bytes, boolean shorter) {
        float result = bytes;
        String suffix = "Byte";
        if (result > 900) {
            suffix = "KB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "MB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "GB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "TB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "PB";
            result = result / 1024;
        }
        String value;
        if (result < 1) {
            value = String.format("%.2f", result);
        } else if (result < 10) {
            if (shorter) {
                value = String.format("%.1f", result);
            } else {
                value = String.format("%.2f", result);
            }
        } else if (result < 100) {
            if (shorter) {
                value = String.format("%.0f", result);
            } else {
                value = String.format("%.2f", result);
            }
        } else {
            value = String.format("%.0f", result);
        }
        return value + suffix;
    }
}