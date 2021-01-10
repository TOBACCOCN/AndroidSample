package com.example.sample.database;

import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author zhangyonghong
 * @date 2020.7.6
 */
public class RealmActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);

        realm = Realm.getDefaultInstance();
        insert();
        update();
        queryWhere();
        delete();
    }

    private void insert() {
        // 1
        // realm.beginTransaction();
        // RealmUserInf realmUserInf = realm.createObject(RealmUserInf.class, 123);
        // realmUserInf.setUserName("zyh");
        // realmUserInf.setPass("zyh");
        // realm.commitTransaction();

        // 2
        // RealmUserInf realmUserInf = new RealmUserInf();
        // realmUserInf.setId(123L);
        // realmUserInf.setUserName("zyh");
        // realmUserInf.setPass("zyh");
        // realm.beginTransaction();
        // realm.copyToRealm(realmUserInf);
        // realm.commitTransaction();

        // 3
        // realm.executeTransaction(realm -> {
        //     RealmUserInf userInf = realm.createObject(RealmUserInf.class, 123);
        //     userInf.setUserName("zyh");
        //     userInf.setPass("zyh");
        // });

        // 4
        RealmUserInf realmUserInf = new RealmUserInf();
        realmUserInf.setId(123L);
        realmUserInf.setUserName("zyh");
        realmUserInf.setPass("zyh");
        realm.executeTransaction(realm -> realm.copyToRealm(realmUserInf));

        query();
    }

    private void update() {
        RealmUserInf realmUserInf = realm.where(RealmUserInf.class)
                .equalTo("userName", "zyh")
                .findFirst();
        if (realmUserInf == null) {
            return;
        }

        realm.beginTransaction();
        realmUserInf.setUserName("zys");
        realmUserInf.setPass("zys");
        realm.commitTransaction();

        query();
    }

    private void delete() {
        RealmResults<RealmUserInf> realmResults = query();
        realm.beginTransaction();
        realmResults.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private RealmResults<RealmUserInf> query() {
        RealmResults<RealmUserInf> realmResults = realm.where(RealmUserInf.class).findAll();
        XLog.d("QUERY_RESULT: [%s]", realmResults);
        return realmResults;
    }

    private RealmResults<RealmUserInf> queryWhere() {
        RealmResults<RealmUserInf> realmResults = realm.where(RealmUserInf.class)
                .equalTo("userName", "zys")
                .in("pass", new String[]{"zys"})
                .sort("userName")
                .contains("userName", "ys")
                .findAll();
        XLog.d("QUERY_RESULT: [%s]", realmResults);
        return realmResults;
    }

}