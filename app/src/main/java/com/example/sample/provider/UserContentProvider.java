package com.example.sample.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.elvishew.xlog.XLog;
import com.example.sample.database.SimpleSQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 用户内容提供者
 *
 * @author TOBACCO
 * @date 2020.09.24
 */
public class UserContentProvider extends ContentProvider {

    private SimpleSQLiteOpenHelper simpleSQLiteOpenHelper;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USER = 1;
    private static final int USERS = 2;
    private static final String TABLE = "user_inf";

    static {
        uriMatcher.addURI("com.example.sample", "user/#", USER);
        uriMatcher.addURI("com.example.sample", "users", USERS);
    }

    // 该方法优先于 Application 的 onCreate 方法启动
    @Override
    public boolean onCreate() {
        simpleSQLiteOpenHelper = new SimpleSQLiteOpenHelper(getContext(), "user.db", null, 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = simpleSQLiteOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor = null;
        switch (match) {
            case USER:
                long id = ContentUris.parseId(uri);
                cursor = database.query(TABLE, projection, "id=?", new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;
            case USERS:
                cursor = database.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = simpleSQLiteOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match) {
            case USER:
            case USERS:
                long insert = database.insert(TABLE, null, values);
                XLog.d("INSERT_RESULT: [%d], ", insert);
                break;
            default:
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = simpleSQLiteOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int delete = 0;
        switch (match) {
            case USER:
                long id = ContentUris.parseId(uri);
                delete = database.delete(TABLE, "id=?", new String[]{String.valueOf(id)});
                XLog.d("DELETE_RESULT: [%d], ", delete);
                break;
            case USERS:
                delete = database.delete(TABLE, selection, selectionArgs);
                XLog.d("DELETE_RESULT: [%d], ", delete);
                break;
            default:
        }
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = simpleSQLiteOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int update = 0;
        switch (match) {
            case USER:
                long id = ContentUris.parseId(uri);
                update = database.update(TABLE, values, "id=?", new String[]{String.valueOf(id)});
                break;
            case USERS:
                update = database.update(TABLE, values, selection, selectionArgs);
                break;
            default:
        }
        XLog.d("UPDATE_RESULT: [%d], ", update);
        return update;
    }

}
