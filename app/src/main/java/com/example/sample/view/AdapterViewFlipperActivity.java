package com.example.sample.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class AdapterViewFlipperActivity extends AppCompatActivity {

    private final int[] images = new int[]{R.drawable.libai, R.drawable.dufu, R.drawable.lishangyin,
            R.drawable.dumu, R.drawable.baijuyi, R.drawable.luyou};
    private AdapterViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_view_flipper);

        flipper = findViewById(R.id.flipper);
        Adapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(AdapterViewFlipperActivity.this);
                imageView.setImageResource(images[position]);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        };
        flipper.setAdapter(adapter);
        flipper.startFlipping();
    }

    public void prev(View view) {
        flipper.showPrevious();
        flipper.stopFlipping();
    }

    public void next(View view) {
        flipper.showNext();
        flipper.stopFlipping();
    }

    public void auto(View view) {
        flipper.startFlipping();
    }
}