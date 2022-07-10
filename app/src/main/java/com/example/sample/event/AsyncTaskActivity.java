// package com.example.sample.event;
//
// import android.content.Context;
// import android.os.AsyncTask;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.ProgressBar;
// import android.widget.TextView;
//
// import com.example.sample.R;
// import com.example.sample.util.ErrorPrintUtil;
//
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.net.URLConnection;
//
// import androidx.appcompat.app.AppCompatActivity;
//
// public class AsyncTaskActivity extends AppCompatActivity {
//
//     private TextView textView;
//
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_async_task);
//
//         textView = findViewById(R.id.tv);
//         DownloadTask downloadTask = new DownloadTask(this, findViewById(R.id.progressbar));
//         try {
//             downloadTask.execute(new URL("https://www.baidu.com"));
//         } catch (MalformedURLException e) {
//             ErrorPrintUtil.printErrorMsg(e);
//         }
//     }
//
//     private class DownloadTask extends AsyncTask<URL, Integer, String> {
//
//         private Context context;
//         private ProgressBar progressBar;
//
//         public DownloadTask(Context context, ProgressBar progressBar) {
//             this.context = context;
//             this.progressBar = progressBar;
//         }
//
//         @Override
//         protected void onPreExecute() {
//             super.onPreExecute();
//             progressBar.setVisibility(View.VISIBLE);
//             progressBar.setProgress(0);
//             progressBar.setMax(3);
//         }
//
//         @Override
//         protected String doInBackground(URL... urls) {
//             try {
//                 URLConnection connection = urls[0].openConnection();
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                 StringBuilder builder = new StringBuilder();
//                 String line;
//                 int count =0;
//                 while((line = reader.readLine()) != null) {
//                     builder.append(line);
//                     ++count;
//                     publishProgress(count);
//                     Thread.sleep(1000);
//                 }
//                 reader.close();
//                 return builder.toString();
//             } catch (Exception e) {
//                 ErrorPrintUtil.printErrorMsg(e);
//             }
//             return null;
//         }
//
//         @Override
//         protected void onProgressUpdate(Integer... values) {
//             super.onProgressUpdate(values);
//             progressBar.setProgress(values[0]);
//         }
//
//         @Override
//         protected void onPostExecute(String s) {
//             super.onPostExecute(s);
//             progressBar.setVisibility(View.GONE);
//             textView.setText(s);
//         }
//     }
// }