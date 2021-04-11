// package com.example.sample.sensor;
//
// import android.Manifest;
// import android.content.BroadcastReceiver;
// import android.content.Context;
// import android.content.Intent;
// import android.content.IntentFilter;
// import android.content.pm.PackageManager;
// import android.net.wifi.WifiManager;
// import android.net.wifi.rtt.RangingRequest;
// import android.net.wifi.rtt.RangingResult;
// import android.net.wifi.rtt.RangingResultCallback;
// import android.net.wifi.rtt.WifiRttManager;
// import android.os.Build;
// import android.os.Bundle;
// import android.widget.TextView;
// import android.widget.Toast;
//
// import com.elvishew.xlog.XLog;
// import com.example.sample.R;
//
// import java.util.Arrays;
// import java.util.List;
// import java.util.concurrent.Executors;
//
// import androidx.annotation.NonNull;
// import androidx.annotation.RequiresApi;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.core.app.ActivityCompat;
//
// /**
//  * https://developer.android.google.cn/guide/topics/connectivity/wifi-rtt?hl=zh-cn
//  *
//  * @author TOBACCO
//  * @date 2021.04.11
//  */
// public class WifiActivity extends AppCompatActivity {
//
//     private WifiManager mWifiManager;
//     private WifiRttManager mWifiRttManager;
//     private TextView mTextView;
//
//     private BroadcastReceiver receiver = new BroadcastReceiver() {
//         @RequiresApi(api = Build.VERSION_CODES.P)
//         @Override
//         public void onReceive(Context context, Intent intent) {
//             // if (mWifiRttManager.isAvailable() && WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
//             if (mWifiRttManager.isAvailable() && WifiRttManager.ACTION_WIFI_RTT_STATE_CHANGED.equals(intent.getAction())) {
//                 startWifiLocation();
//             }
//         }
//     };
//
//     @RequiresApi(api = Build.VERSION_CODES.P)
//     private void startWifiLocation() {
//         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//             return;
//         }
//         RangingRequest request = new RangingRequest.Builder().addAccessPoints(mWifiManager.getScanResults()).build();
//         mWifiRttManager.startRanging(request, Executors.newCachedThreadPool(), new RangingResultCallback() {
//             @Override
//             public void onRangingFailure(int code) {
//
//             }
//
//             @Override
//             public void onRangingResults(@NonNull List<RangingResult> results) {
//                 for (RangingResult result : results) {
//                     mTextView.setText("MAC_ADDRESS: " + result.getMacAddress() + "\nDISTANCE: " + result.getDistanceMm());
//                 }
//             }
//         });
//     }
//
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_wifi);
//
//         if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_RTT)) {
//             XLog.d("NONE_FEATURE_WIFI_RTT");
//             return;
//         }
//
//         requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0x11);
//
//         mTextView = findViewById(R.id.tv);
//         IntentFilter intentFilter = new IntentFilter();
//         intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//         intentFilter.addAction(WifiRttManager.ACTION_WIFI_RTT_STATE_CHANGED);
//         intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//         intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//         registerReceiver(receiver, intentFilter);
//         mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//         mWifiRttManager = (WifiRttManager) getSystemService(WIFI_RTT_RANGING_SERVICE);
//     }
//
//     @Override
//     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//         if (0x11 == requestCode) {
//             Integer[] grants = new Integer[grantResults.length];
//             for (int i = 0; i < grantResults.length; ++i) {
//                 grants[i] = grantResults[i];
//             }
//             if (Arrays.asList(grants).contains(PackageManager.PERMISSION_DENIED)) {
//                 Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
//                 finish();
//             }
//         }
//     }
// }