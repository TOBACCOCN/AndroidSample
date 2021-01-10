package com.example.sample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.SimpleAdapter;
import android.widget.StackView;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class StackViewActivity extends AppCompatActivity {

    private final int[] images = new int[]{R.drawable.libai, R.drawable.dufu, R.drawable.lishangyin,
            R.drawable.dumu, R.drawable.baijuyi, R.drawable.luyou};
    private StackView stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view);

        stackView = findViewById(R.id.stack);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i : images) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", i);
            list.add(map);
        }
        Adapter adapter = new SimpleAdapter(this, list, R.layout.cell, new String[]{"image"}, new int[]{R.id.pic});
        stackView.setAdapter(adapter);
    }

    public void prev(View view) {
        stackView.showPrevious();
    }

    public void next(View view) {
        stackView.showNext();
    }
}