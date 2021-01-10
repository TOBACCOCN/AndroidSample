package com.example.sample.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        list.add("唐僧");
        list.add("孙悟空");
        list.add("猪八戒");
        list.add("沙悟净");

        // ArrayAdapter
        // ListAdapter adapter = new ArrayAdapter<>(this, R.layout.array_item, R.id.tv, list);

        // BaseAdapter
        ListAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @SuppressLint("ViewHolder")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LinearLayout linearLayout = new LinearLayout(ListViewActivity.this);
                ImageView imageView = new ImageView(ListViewActivity.this);
                imageView.setImageResource(R.drawable.ic_launcher);
                linearLayout.addView(imageView);
                TextView textView = new TextView(ListViewActivity.this);
                textView.setText(list.get(position));
                linearLayout.addView(textView);
                return linearLayout;
            }
        };

        listView.setAdapter(adapter);

    }
}