package com.example.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    private static final String TAG = "RemoteService";

    SimpleData mSimpleData;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "ON_CREATE");
        initSimpleData();
    }

    /**
     * 初始化 SimpleData 数据
     **/
    private void initSimpleData() {
        mSimpleData = new SimpleData();
        mSimpleData.setProvince("gd");
        mSimpleData.setCity("sz");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ON_BIND");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "ON_UNBIND");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "ON_DESTROY");
        super.onDestroy();
    }

    /**
     * 实现 IRemoteService.aidl 中定义的方法
     */
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

        @Override
        public int getPid() throws RemoteException {
            return android.os.Process.myPid();
        }

        @Override
        public SimpleData getSimpleData() throws RemoteException {
            return mSimpleData;
        }

        /** 此处可用于权限拦截 **/
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };
}
