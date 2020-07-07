package com.example.sample;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.example.sample.dao.UserInf;
import com.example.sample.dao.UserInfDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangyonghong
 * @date 2020.7.3
 */
public class GreenDAOActivity extends AppCompatActivity {

    private UserInfDao userInfDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        userInfDao = DefaultApplication.getDaoSession().getUserInfDao();
        insert();
        update();
        queryWhere();
        // delete();
        // drop();
        // deleteDB();
    }

    private void insert() {
        UserInf userInf = new UserInf();
        userInf.setUserName("zyh");
        userInf.setPass("zyh");
        long insert = userInfDao.insert(userInf);
        XLog.d(">>>>> INSERT_AFFECT: [%s]", insert);
        query();
    }

    private void update() {
        UserInf userInf = query().get(0);
        userInf.setUserName("zys");
        userInf.setPass("zys");
        userInfDao.update(userInf);
        query();
    }

    private void delete() {
        userInfDao.deleteAll();
        query();
    }

    private List<UserInf> query() {
        List<UserInf> userInfs = userInfDao.loadAll();
        XLog.d(">>>>> QUERY_RESULT: [%s]", userInfs);
        return userInfs;
    }

    private List<UserInf> queryWhere() {
        QueryBuilder<UserInf> query = userInfDao.queryBuilder().where(UserInfDao.Properties.UserName.eq("zys"));
        List<UserInf> userInfs = query.list();
        XLog.d(">>>>> QUERY_RESULT: [%s]", userInfs);
        return userInfs;
    }

    private void drop() {
        DefaultApplication.getDevOpenHelper().getWritableDatabase().execSQL("drop table user_inf");
    }

    private void deleteDB() {
        boolean delete = SQLiteDatabase.deleteDatabase(getDatabasePath("GreenDAO.db"));
        XLog.d(">>>>> DELETE_DB_SUCCESS: [%s]", delete);
    }


}