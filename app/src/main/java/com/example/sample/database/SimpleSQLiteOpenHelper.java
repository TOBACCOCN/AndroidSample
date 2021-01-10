package com.example.sample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elvishew.xlog.XLog;

import androidx.annotation.Nullable;

/**
 * @author zhangyonghong
 * @date 2020.7.2
 */
public class SimpleSQLiteOpenHelper extends SQLiteOpenHelper {

    public SimpleSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 第一次创建名为 name 的 .db 文件时，才会触发该 onCreate
        String sql = "create table user_inf(id integer primary key autoincrement, user_name varchar(255), pass varchar(255))";
        db.execSQL(sql);
        XLog.d("USER_INF_CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
