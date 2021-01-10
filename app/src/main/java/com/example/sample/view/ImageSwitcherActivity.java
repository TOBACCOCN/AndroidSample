package com.example.sample.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class ImageSwitcherActivity extends AppCompatActivity {

    private final int[] images = new int[]{R.drawable.ic_launcher, R.drawable.libai, R.drawable.ic_launcher, R.drawable.dufu,
            R.drawable.lishangyin, R.drawable.ic_launcher, R.drawable.dumu, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.baijuyi, R.drawable.ic_launcher, R.drawable.luyou};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);

        GridView gridView = findViewById(R.id.grid);
        List<Map<String, Object>> data =new ArrayList<>();
        for (int image: images) {
            Map<String, Object> map =new HashMap<>();
            map.put("image", image);
            data.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(this, data, R.layout.cell, new String[]{"image"}, new int[]{R.id.pic});
        gridView.setAdapter(adapter);

        ImageSwitcher imageSwitcher = findViewById(R.id.switcher);
        imageSwitcher.setFactory(() -> {
            ImageView imageView =new ImageView(ImageSwitcherActivity.this);
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> imageSwitcher.setImageResource(images[position]));
    }
}