package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangyonghong
 * @date 2020.7.2
 */
public class MainActivity extends AppCompatActivity {

    private Map<Integer, Class<? extends AppCompatActivity>> imageSrcId2ActivityMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.libai, R.drawable.dufu, R.drawable.lishangyin,
                R.drawable.dumu, R.drawable.luyou, R.drawable.baijuyi, R.drawable.sqlite, R.drawable.green_dao,
                R.drawable.wcdb, R.drawable.realm, R.drawable.object_box));

        List<String> names = new ArrayList<>(Arrays.asList("李白", "杜甫", "李商隐", "杜牧", "陆游", "白居易",
                "SQLiteOpenHelper", "greenDAO", "WCDB", "realm", "ObjectBox"));

        initListView(images, names);

        imageSrcId2ActivityMap.put(R.drawable.sqlite, SQLiteOpenHelperActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.green_dao, GreenDAOActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.wcdb, WCDBActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.realm, RealmActivity.class);
        imageSrcId2ActivityMap.put(R.drawable.object_box, ObjectBoxActivity.class);
    }

    private void initListView(List<Integer> images, List<String> names) {
        ListView listView = findViewById(R.id.list);
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", images.get(i));
            item.put("name", names.get(i));
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.simple_item,
                new String[]{"image", "name"}, new int[]{R.id.iv, R.id.tv});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
                    XLog.d(">>>>> position: [%s], id: [%s]", position, id);
                    @SuppressWarnings("unchecked")
                    Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                    int imageSrcId = (int) item.get("image");
                    Class<? extends AppCompatActivity> clazz = imageSrcId2ActivityMap.get(imageSrcId);
                    if (clazz != null) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, clazz);
                        startActivity(intent);
                    }
                }
        );
    }


}