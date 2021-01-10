package com.example.sample.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        AutoCompleteTextView actv = findViewById(R.id.auto);
        List<String> list = new ArrayList<>();
        list.add("疯狂 Java 讲义");
        list.add("疯狂 Android 讲义");
        list.add("疯狂 Python 讲义");
        list.add("疯狂 XML 讲义");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.array_item, R.id.tv, list);
        actv.setAdapter(adapter);
    }
}