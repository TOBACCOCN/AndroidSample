package com.example.sample.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.example.sample.R;
import com.example.sample.view.ImageViewActivity;
import com.example.sample.view.ListViewActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SimpleLauncherActivity extends AppCompatActivity {

    private final String[] names = new String[]{"ListView", "ImageView"};
    private final Class[] classes = new Class[]{ListViewActivity.class, ImageViewActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_launcher);
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_simple_launcher, names);
        // setListAdapter(adapter);
    }

    // @Override
    // protected Intent intentForPosition(int position) {
    //     return new Intent(this, classes[position]);
    // }
}