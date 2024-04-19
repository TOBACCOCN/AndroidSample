package com.example.sample.connection;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;

public class BluetoothServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_server);

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            // 开启蓝牙
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
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