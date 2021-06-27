package com.example.sample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.example.sample.dao.DaoMaster;
import com.example.sample.dao.DaoSession;
import com.example.sample.database.MyObjectBox;
import com.example.sample.database.SimpleSQLiteOpenHelper;
import com.example.sample.database.WCDBSQLiteOpenHelper;
import com.example.sample.mqtt.DefaultMqttCallBack;
import com.example.sample.util.ErrorPrintUtil;
import com.example.sample.util.MD5Util;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author zhangyonghong
 * @date 2020.7.3
 */
public class DefaultApplication extends Application {

    private static SimpleSQLiteOpenHelper simpleSQLiteOpenHelper;
    @SuppressLint("StaticFieldLeak")
    private static DaoMaster.DevOpenHelper sDevOpenHelper;
    private static DaoSession sDaoSession;
    private static WCDBSQLiteOpenHelper sWcdbSQLiteOpenHelper;
    private static BoxStore sBoxStore;
    private static MqttClient sMqttClient;

    @Override
    public void onCreate() {
        super.onCreate();
        initXLog();
        initSimpleSQLiteOpenHelper();
        initGreenDAO();
        initWCDB();
        initRealm();
        initObjectBox();
        initMQTT();
    }

    private void initXLog() {
        // https://www.oschina.net/p/xlog?hmsr=aladdin1e1
        LogConfiguration config = new LogConfiguration.Builder()
                // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE)
                // 指定 TAG，默认为 "X-LOG"
                .tag("SAMPLE_LOG")
                // 允许打印线程信息，默认禁止
                .t()
                // 允许打印深度为 2 的调用栈信息，默认禁止
                .st(2)
                // 允许打印日志边框，默认禁止
                // .b()
                // 添加黑名单 TAG 过滤器
                .addInterceptor(new BlacklistTagsFilterInterceptor("blacklist_tag"))
                .build();

        // 通过 System.out 打印日志到控制台的打印器
        // Printer consolePrinter = new ConsolePrinter();
        // 通过 android.util.Log 打印日志的打印器
        Printer androidPrinter = new AndroidPrinter();
        // 打印日志到文件的打印器
        Printer filePrinter = new FilePrinter
                // 指定保存日志文件的路径
                .Builder(getFilesDir().getAbsolutePath())
                // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .fileNameGenerator(new DateFileNameGenerator())
                // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                .backupStrategy(new NeverBackupStrategy())
                // 指定日志文件清除策略，默认为 NeverCleanStrategy()
                .cleanStrategy(new FileLastModifiedCleanStrategy((long) 1000 * 60 * 60 * 24 * 30))
                .build();

        // 初始化 XLog
        XLog.init(
                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                config,
                // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
                androidPrinter, filePrinter);
    }

    private void initSimpleSQLiteOpenHelper() {
        if (getResources().getBoolean(R.bool.sqlite_enable)) {
            simpleSQLiteOpenHelper = new SimpleSQLiteOpenHelper(this, "SQLiteOpenHelper.db", null, 1);
        }
    }

    public static SimpleSQLiteOpenHelper getSimpleSQLiteOpenHelper() {
        return simpleSQLiteOpenHelper;
    }

    private void initGreenDAO() {
        if (!getResources().getBoolean(R.bool.green_dao_enable)) {
            return;
        }
        sDevOpenHelper = new DaoMaster.DevOpenHelper(this, "GreenDAO.db");
        // DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoMaster daoMaster = new DaoMaster(sDevOpenHelper.getEncryptedWritableDb("sample"));
        sDaoSession = daoMaster.newSession();
    }

    public static DaoMaster.DevOpenHelper getsDevOpenHelper() {
        return sDevOpenHelper;
    }

    public static DaoSession getsDaoSession() {
        return sDaoSession;
    }

    private void initWCDB() {
        if (getResources().getBoolean(R.bool.wcdb_enable)) {
            sWcdbSQLiteOpenHelper = new WCDBSQLiteOpenHelper(this, "sample".getBytes());
        }
    }

    public static WCDBSQLiteOpenHelper getWCDBSQLiteOpenHelper() {
        return sWcdbSQLiteOpenHelper;
    }

    private void initRealm() {
        if (!getResources().getBoolean(R.bool.realm_enable)) {
            return;
        }
        Realm.init(this);
        String md5Encrypted;
        try {
            md5Encrypted = MD5Util.encrypt("sample".getBytes());
        } catch (NoSuchAlgorithmException e) {
            ErrorPrintUtil.printErrorMsg(e);
            return;
        }
        // encryptionKey 必须得是 64 位
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .encryptionKey((md5Encrypted + md5Encrypted).getBytes()).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void initObjectBox() {
        if (!getResources().getBoolean(R.bool.object_box_enable)) {
            return;
        }
        //第一次没运行之前，MyObjectBox默认会有报错提示，可以忽略。创建实体类， make之后报错就会不提示
        sBoxStore = MyObjectBox.builder().androidContext(this).build();
        //开启浏览器访问ObjectBox
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(sBoxStore).start(this);
            XLog.d("OBJECT_BROWSER_STARTED: [%s]", started);
        }
    }

    public static BoxStore getsBoxStore() {
        return sBoxStore;
    }

    private void initMQTT() {
        if (!getResources().getBoolean(R.bool.mqtt_enable)) {
            return;
        }
        String clientId = UUID.randomUUID().toString().replaceAll("-", "");
        String serverURI = getString(R.string.mqtt_server_uri);
        try {
            sMqttClient = new MqttClient(serverURI, clientId, new MemoryPersistence());

            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(getString(R.string.mqtt_username));
            connOpts.setPassword(getString(R.string.mqtt_password).toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);

            // 设置回调
            sMqttClient.setCallback(new DefaultMqttCallBack());

            // 建立连接
            Log.i("TAG", ">>>>> MQTT CONNECTING TO SERVER_URI:  " + serverURI);
            try {
                sMqttClient.connect(connOpts);
            } catch (MqttException e) {
                ErrorPrintUtil.printErrorMsg(e);
            }

            // 订阅
            sMqttClient.subscribe(getString(R.string.mqtt_topic_filter));
        } catch (MqttException e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }

    public static MqttClient getMqttClient() {
        return sMqttClient;
    }

}
