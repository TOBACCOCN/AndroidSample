package com.example.sample.connection;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BluetoothServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_server);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            // 开启蓝牙
            bluetoothAdapter.enable();
            // startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
        }

        // 启用可检测性
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        startBluetoothServerSocket(bluetoothAdapter, this);
    }

    @SuppressLint("HandlerLeak")
    private void startBluetoothServerSocket(BluetoothAdapter bluetoothAdapter, Context context) {
        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_LONG).show();
            }
        };
        try {
            BluetoothServerSocket bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("SIMPLE_BLUETOOTH_SERVER_SOCKET",
                    // UUID.fromString(UUID.randomUUID().toString()));
                    UUID.fromString("69c9f600-6c56-4d60-82b3-635d43f78864"));
            new Thread(() -> {
                try {
                    while (true) {
                        BluetoothSocket bluetoothSocket = bluetoothServerSocket.accept();
                        InputStream inputStream = bluetoothSocket.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = inputStream.read()) != 0) {
                            baos.write(buf, 0, len);
                        }
                        XLog.i("from client: [%s]", baos.toString());
                        Message message = handler.obtainMessage();
                        message.obj = baos.toString();
                        message.sendToTarget();

                        OutputStream outputStream = bluetoothSocket.getOutputStream();
                        outputStream.write("I'm bluetooth server socket".getBytes());
                        bluetoothSocket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}