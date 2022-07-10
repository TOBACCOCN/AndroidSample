package com.example.sample.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.example.sample.DefaultApplication;
import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangyonghong
 * @date 2020.7.3
 */
public class SQLiteOpenHelperActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_open_helper);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            XLog.i("EXTRA_NAME: [%s], EXTRA_AGE: [%s]", extras.getString("name"), extras.getString("age"));
        }

        database = DefaultApplication.getSimpleSQLiteOpenHelper().getWritableDatabase();
        database.beginTransaction();
        insert();
        update();
        // delete();
        database.endTransaction();
        // drop();
        // deleteDB();

        // doContentResolver();
    }

    // @SuppressLint("Range")
    private void doContentResolver() {
        ContentResolver contentResolver = getContentResolver();
        Uri url = Uri.parse("content://com.example.sample/users");
        ContentValues values = new ContentValues();
        values.put("user_name", "zyh");
        values.put("pass", "zyh");
        contentResolver.insert(url, values);
        values.put("pass", "zys");
        // contentResolver.update(url, values, "user_name=?", new String[]{"zyh"});
        contentResolver.update(url, values, null, null);
        Cursor cursor = contentResolver.query(url, null, "user_name=?", new String[]{"zyh"}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String pass = cursor.getString(cursor.getColumnIndex("pass"));
                XLog.d("ID: [%s], USER_NAME: [%s], PASS: [%s]", id, userName, pass);
            }
            cursor.close();
        }
        // contentResolver.delete(url, "user_name=?", new String[]{"zyh"});
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put("user_name", "zyh");
        values.put("pass", "zyh");
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

    // @SuppressLint("Range")
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

    private void delete() {
        long delete = database.delete("user_inf", null, null);
        // long delete = database.delete("user_inf", "user_name = ?", new String[]{"zys"});
        XLog.d("DELETE_AFFECT: [%s]", delete);
        query();
    }

    private void drop() {
        database.execSQL("drop table user_inf");
    }

    private void deleteDB() {
        boolean delete = SQLiteDatabase.deleteDatabase(getDatabasePath("SQLiteOpenHelper.db"));
        XLog.d("DELETE_DB_SUCCESS: [%s]", delete);
    }

}