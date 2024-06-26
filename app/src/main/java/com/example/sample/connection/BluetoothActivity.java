package com.example.sample.connection;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class BluetoothActivity extends AppCompatActivity {

    private static final String BLUETOOTH = Manifest.permission.BLUETOOTH;
    private static final String BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_FINE_LOCATION));
    private BroadcastReceiver mReceiver;
    private boolean connected;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!Arrays.asList(permissions).containsAll(this.permissions) || Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
            Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        // 其中之一的权限（似乎是ACCESS_FINE_LOCATION）必须手动申请，不然 startDiscovery 返回 false
        requestPermissions(new String[]{BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_FINE_LOCATION}, 0x11);

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            return;
        }

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


        // 发现设备
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (bluetoothDevice != null) {
                        if (ActivityCompat.checkSelfPermission(BluetoothActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        String name = bluetoothDevice.getName();
                        String address = bluetoothDevice.getAddress();
                        XLog.i("name: [%s], address: [%s]", name, address);

                        if ("红米手机".equals(name)) {
                            XLog.i("uuid: [%s], ", UUID.randomUUID().toString());
                            // 连接设备
                            if (!connected) {
                                bluetoothAdapter.cancelDiscovery();
                                connectBluetoothDevice(bluetoothDevice, context);
                                connected = true;
                            }
                        }
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    // 停止发现
                    // bluetoothAdapter.cancelDiscovery();
                    XLog.i("cancelDiscovery");
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, intentFilter);
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        boolean result = bluetoothAdapter.startDiscovery();
        XLog.i("startDiscovery result: [%s]", result);


        // // 启用可检测性
        // Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        // startActivity(discoverableIntent);


        // 获取已配对设备
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (bondedDevices.size() == 0) {
            return;
        }

        for (BluetoothDevice device : bondedDevices) {
            String name = device.getName();
            String address = device.getAddress();
            XLog.i("name: [%s], address: [%s]", name, address);
        }

        new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // 关闭蓝牙
                // bluetoothAdapter.disable();
            }
        }.sendEmptyMessageDelayed(0, 10000);
    }

    private void connectBluetoothDevice(BluetoothDevice bluetoothDevice, Context context) {
        try {
            Handler handler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_LONG).show();
                }
            };
            // bluetoothDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID.randomUUID().toString()));
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
            BluetoothSocket socket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("69c9f600-6c56-4d60-82b3-635d43f78864"));
            new Thread(() -> {
                try {
                    socket.connect();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("I'm bluetooth client socket".getBytes());

                    InputStream inputStream = socket.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = inputStream.read()) != 0) {
                        baos.write(buf, 0, len);
                    }
                    XLog.i("from server: [%s]", baos.toString());
                    Message message = handler.obtainMessage();
                    message.obj = baos.toString();
                    message.sendToTarget();

                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregisterReceiver(mReceiver);
    }
}