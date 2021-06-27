package com.example.sample;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.database.GreenDAOActivity;
import com.example.sample.database.ObjectBoxActivity;
import com.example.sample.database.RealmActivity;
import com.example.sample.database.SQLiteOpenHelperActivity;
import com.example.sample.database.WCDBActivity;
import com.example.sample.service.SimpleBindService;
import com.example.sample.service.SimpleIntentService;
import com.example.sample.service.SimpleService;
import com.example.sample.util.ErrorPrintUtil;
import com.tencent.mmkv.MMKV;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangyonghong
 * @date 2020.7.2
 */
public class MainActivity extends AppCompatActivity {

    private final Map<Integer, Class<? extends AppCompatActivity>> imageSrcId2ActivityMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.libai, R.drawable.dufu, R.drawable.lishangyin,
                R.drawable.dumu, R.drawable.luyou, R.drawable.baijuyi, R.drawable.sqlite, R.drawable.green_dao,
                R.drawable.wcdb, R.drawable.realm, R.drawable.object_box));

        List<String> names = new ArrayList<>(Arrays.asList("李白", "杜甫", "李商隐", "杜牧", "陆游", "白居易",
                "SQLiteOpenHelper", "greenDAO", "WCDB", "realm", "ObjectBox"));

        initListView(images, names);

        imageSrcId2ActivityMap.put(R.drawable.sqlite, SQLiteOpenHelperActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.green_dao, GreenDAOActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.wcdb, WCDBActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.realm, RealmActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.object_box, ObjectBoxActivity.class);

        startService();

        showIntent();

        activityManagerTest();

        // localSocketTest();

        MMKVTest();
    }

    private void MMKVTest() {
        String rootDir = MMKV.initialize(this);
        XLog.d("ROOT_DIR: [%s]", rootDir);

        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode("bool", true);
        mmkv.encode("int", 100);
        mmkv.encode("float", 3.14f);
        mmkv.encode("long", Long.MAX_VALUE);
        mmkv.encode("bytes", new byte[]{3, 1, 4});
        XLog.d("BOOL: [%s], INT: [%d], FLOAT: [%f], LONG: [%d]", mmkv.decodeBool("bool"), mmkv.decodeInt("int"),
                mmkv.decodeFloat("float"), mmkv.decodeLong("long"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void localSocketTest() {
        new Thread(() -> {
            LocalSocket localSocket = new LocalSocket();
            try {
                localSocket.connect(new LocalSocketAddress("SIMPLE_SERVER_SOCKET"));
                OutputStream outputStream = localSocket.getOutputStream();
                String time = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now());
                outputStream.write(("Hello, I'm LocalSocket Client" +time ).getBytes());
                InputStream inputStream = localSocket.getInputStream();
                ByteArrayOutputStream baos =new ByteArrayOutputStream();
                byte[] buf =new byte[8192];
                int len;
                while ((len = inputStream.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                android.util.Log.d("TAG", new String(baos.toByteArray(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void activityManagerTest() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        XLog.d("AVAIL_MEM: [%s], LOW_MEMORY: [%s], THRESHOLD: [%s], TOTAL_MEM: [%s]",
                memoryInfo.availMem, memoryInfo.lowMemory, memoryInfo.threshold, memoryInfo.totalMem);
    }

    private void showIntent() {
        Toast.makeText(MainActivity.this, "ACTIVITY_CUR_PID: " + android.os.Process.myPid(), Toast.LENGTH_LONG).show();
        XLog.i("ACTIVITY_CUR_PID: " + android.os.Process.myPid());
        Intent intent = getIntent();
        ComponentName component = intent.getComponent();
        String packageName = component.getPackageName();
        String className = component.getClassName();
        String action = intent.getAction();
        Set<String> categories = intent.getCategories();
        Uri data = intent.getData();
        String type = intent.getType();
        XLog.d("PACKAGE_NAME: [%s], CLASS_NAME: [%s], ACTION: [%s], CATEGORIES: [%s], DATA: [%s], TYPE: [%s]",
                packageName, className, action, categories, data, type);

        Message obtain = Message.obtain();
        XLog.i("MESSAGE: [%s]", obtain);
    }

    private void startService() {
        Intent intent = new Intent(this, SimpleService.class);
        // startService(intent);
        // stopService(intent);

        intent = new Intent(this, SimpleIntentService.class);
        // startService(intent);
        // stopService(intent);

        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                XLog.i("SERVICE_PACKAGE_NAME: [%s]", ((SimpleBindService.SimpleBinder) service).getServicePackageName());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(new Intent(this, SimpleBindService.class), serviceConnection, BIND_AUTO_CREATE);
        // unbindService(serviceConnection);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IRemoteService remoteService = IRemoteService.Stub.asInterface(service);
                try {
                    SimpleData simpleData = remoteService.getSimpleData();
                    Toast.makeText(MainActivity.this, simpleData.toString() + "\nPID: " + remoteService.getPid(), Toast.LENGTH_LONG).show();
                    XLog.i("REMOTE_PID: [%d], SIMPLE_DATA: [%s]", remoteService.getPid(), simpleData);
                } catch (RemoteException e) {
                    ErrorPrintUtil.printErrorMsg(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        intent = new Intent();
        intent.setClassName("com.example.sample", "com.example.sample.RemoteService");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        // unbindService(serviceConnection);
    }

    private void initListView(List<Integer> images, List<String> names) {
        ListView listView = findViewById(R.id.list);
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", images.get(i));
            item.put("name", names.get(i));
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.simple_item,
                new String[]{"image", "name"}, new int[]{R.id.iv, R.id.tv});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
                    XLog.d("position: [%s], id: [%s]", position, id);
                    @SuppressWarnings("unchecked")
                    Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                    int imageSrcId = (int) item.get("image");
                    Class<? extends AppCompatActivity> clazz = imageSrcId2ActivityMap.get(imageSrcId);
                    if (clazz != null) {
                        Intent intent = new Intent();
                        intent.putExtra("name", "zhansan");
                        intent.putExtra("age", "13");
                        intent.setClass(MainActivity.this, clazz);
                        startActivity(intent);
                    }
                }
        );
    }


}