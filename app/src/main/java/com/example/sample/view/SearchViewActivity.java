package com.example.sample.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private final String[] books = new String[]{"疯狂 Android 讲义", "疯狂 Java 讲义", "疯狂 Python 讲义",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        SearchView searchView = findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        // 设置搜索框是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("查找");

        listView = findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.array_item, R.id.tv, books));
        listView.setTextFilterEnabled(true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "查询内容：" + query, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }
}