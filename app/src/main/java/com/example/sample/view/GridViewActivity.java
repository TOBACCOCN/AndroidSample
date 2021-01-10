package com.example.sample.view;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class GridViewActivity extends AppCompatActivity {

    private final int[] images = new int[]{R.drawable.ic_launcher, R.drawable.libai, R.drawable.ic_launcher, R.drawable.dufu,
            R.drawable.lishangyin, R.drawable.ic_launcher, R.drawable.dumu, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.baijuyi, R.drawable.ic_launcher, R.drawable.luyou};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridView = findViewById(R.id.grid);
        List<Map<String, Object>> itemList = new ArrayList<>();
        for (int i : images) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", i);
            itemList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(this, itemList, R.layout.cell, new String[]{"image"}, new int[]{R.id.pic});
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            ImageView imageView = findViewById(R.id.image);
            imageView.setImageResource(images[position]);
        });
    }
}