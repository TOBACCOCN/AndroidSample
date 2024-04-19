// package com.example.sample.view;
//
// import android.app.ProgressDialog;
// import android.os.Bundle;
// import android.os.Handler;
// import android.os.Looper;
// import android.os.Message;
// import android.view.View;
//
// import com.example.sample.R;
// import com.example.sample.util.ErrorPrintUtil;
//
// import java.util.concurrent.TimeUnit;
//
// import androidx.annotation.NonNull;
// import androidx.appcompat.app.AppCompatActivity;
//
// public class ProgressDialogActivity extends AppCompatActivity {
//
//     private ProgressDialog progressDialog;
//     private int currentStatus = 0;
//
//     private Handler handler = new Handler(Looper.myLooper()) {
//         @Override
//         public void handleMessage(@NonNull Message msg) {
//             if (msg.what == 111) {
//                 progressDialog.setProgress(currentStatus);
//             }
//         }
//     };
//
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_progress_dialog);
//     }
//
//     public void showSpinner(View view) {
//         ProgressDialog.show(this, "任务执行中", "请稍等", false, true);
//     }
//
//     public void showIndeterminate(View view) {
//         ProgressDialog progressDialog = new ProgressDialog(this);
//         progressDialog.setTitle("任务正在执行中");
//         progressDialog.setMessage("任务执行中，请稍等。。。");
//         progressDialog.setCancelable(true);
//         progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//         progressDialog.setIndeterminate(true);
//         progressDialog.show();
//     }
//
//     public void showProgress(View view) {
//         progressDialog = new ProgressDialog(this);
//         progressDialog.setMax(100);
//         progressDialog.setTitle("完成进度");
//         progressDialog.setMessage("当前任务完成的百分比");
//         progressDialog.setCancelable(false);
//         progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//         // 设置是否显示进度，false 表示显示进度
//         progressDialog.setIndeterminate(false);
//         progressDialog.show();
//
//         new Thread(() -> {
//             while (currentStatus < 100) {
//                 ++currentStatus;
//                 handler.sendEmptyMessage(111);
//                 try {
//                     TimeUnit.SECONDS.sleep(1);
//                 } catch (InterruptedException e) {
//                     ErrorPrintUtil.printErrorMsg(e);
//                 }
//             }
//             progressDialog.dismiss();
//         }).start();
//     }
// }