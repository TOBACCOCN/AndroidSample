package com.example.sample;

import android.os.Bundle;

import com.elvishew.xlog.XLog;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.objectbox.Box;

/**
 * @author zhangyonghong
 * @date 2020.7.6
 */
public class ObjectBoxActivity extends AppCompatActivity {

    private Box<ObjectBoxUserInf> box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_box);

        box = DefaultApplication.getBoxStore().boxFor(ObjectBoxUserInf.class);
        insert();
        update();
        queryWhere();
        delete();
    }

    private void insert() {
        ObjectBoxUserInf userInf = new ObjectBoxUserInf();
        userInf.setUserName("zyh");
        userInf.setPass("zyh");
        long insert = box.put(userInf);
        XLog.d(">>>>> INSERT_AFFECT: [%s]", insert);
        query();
    }

    private void update() {
        ObjectBoxUserInf userInf = query().get(0);
        userInf.setUserName("zys");
        userInf.setPass("zys");
        box.put(userInf);
        query();
    }

    private void delete() {
        box.removeAll();
        query();
    }

    private List<ObjectBoxUserInf> query() {
        List<ObjectBoxUserInf> userInfs = box.query().order(ObjectBoxUserInf_.userName).build().find(0, 10);
        XLog.d(">>>>> QUERY_RESULT: [%s]", userInfs);
        return userInfs;
    }

    private List<ObjectBoxUserInf> queryWhere() {
        List<ObjectBoxUserInf> userInfs = box.query().equal(ObjectBoxUserInf_.userName, "zys").build().find();
        XLog.d(">>>>> QUERY_RESULT: [%s]", userInfs);
        return userInfs;
    }

}