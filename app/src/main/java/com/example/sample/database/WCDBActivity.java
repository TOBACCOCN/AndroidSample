package com.example.sample.database;

import android.content.ContentValues;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.example.sample.DefaultApplication;
import com.example.sample.R;
import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangyonghong
 * @date 2020.7.6
 */
public class WCDBActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wcdb);

        database = DefaultApplication.getWCDBSQLiteOpenHelper().getWritableDatabase();
        database.beginTransaction();
        insert();
        update();
        queryWhere();
        delete();
        database.endTransaction();
        // drop();
        // deleteDB();
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put("user_name", "zyh");
        values.put("pass", "zyh");
        // 执行插入语句
        long insert = database.insert("user_inf", null, values);
        XLog.d("INSERT_AFFECT: [%s]", insert);
        query();
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("user_name", "zys");
        values.put("pass", "zys");
        long update = database.update("user_inf", values, "user_name = ?", new String[]{"zyh"});
        XLog.d("UPDATE_AFFECT: [%s]", update);
        query();
    }

    private void delete() {
        long delete = database.delete("user_inf", null, null);
        // long delete = database.delete("user_inf", "user_name = ?", new String[]{"zys"});
        XLog.d("DELETE_AFFECT: [%s]", delete);
        query();
    }

    private void query() {
        Cursor cursor = database.query("user_inf", null, null, null, null, null, null);
        XLog.d("QUERY_RESULT_COUNT: [%s]", cursor.getCount());
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));
            String pass = cursor.getString(cursor.getColumnIndex("pass"));
            XLog.d("ID: [%s], USER_NAME: [%s], PASS: [%s]", id, userName, pass);
        }
        cursor.close();
    }

    private void queryWhere() {
        Cursor cursor = database.query("user_inf", new String[]{"id", "user_name", "pass"},
                "user_name = ?", new String[]{"zys"}, null, null, null);
        XLog.d("QUERY_RESULT_COUNT: [%s]", cursor.getCount());
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));
            String pass = cursor.getString(cursor.getColumnIndex("pass"));
            XLog.d("ID: [%s], USER_NAME: [%s], PASS: [%s]", id, userName, pass);
        }
        cursor.close();
    }

    private void drop() {
        DefaultApplication.getWCDBSQLiteOpenHelper().getWritableDatabase().execSQL("drop table user_inf");
    }

    private void deleteDB() {
        boolean delete = DefaultApplication.getWCDBSQLiteOpenHelper().onDelete();
        XLog.d("DELETE_DB_SUCCESS: [%s]", delete);
    }

}