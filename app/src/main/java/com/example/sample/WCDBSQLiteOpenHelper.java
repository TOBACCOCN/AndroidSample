package com.example.sample;

import android.content.Context;

import com.elvishew.xlog.XLog;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * @author zhangyonghong
 * @date 2020.7.6
 */
public class WCDBSQLiteOpenHelper extends SQLiteOpenHelper {

    // 数据库 db 文件名称
    private static final String DEFAULT_NAME = "wcdb.db";
    // 默认版本号
    private static final int DEFAULT_VERSION = 1;

    private Context context;

    public WCDBSQLiteOpenHelper(Context context) {
        super(context, DEFAULT_NAME, null, DEFAULT_VERSION);
        this.context = context;
    }

    public WCDBSQLiteOpenHelper(Context context, byte[] password) {
        super(context, DEFAULT_NAME, password, null, DEFAULT_VERSION, null);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 第一次创建名为 name 的 .db 文件时，才会触发该 onCreate
        String sql = "create table user_inf(id integer primary key autoincrement, user_name varchar(255), pass varchar(255))";
        db.execSQL(sql);
        XLog.d(">>>>> USER_INF_CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        XLog.d(">>>>> OLD_VERSION: [%s],NEW_VERSION: [%s]", oldVersion, newVersion);
    }

    /**
     * 删除数据库 db 文件
     */
    public boolean onDelete() {
        return SQLiteDatabase.deleteDatabase(context.getDatabasePath(DEFAULT_NAME));
    }
}
