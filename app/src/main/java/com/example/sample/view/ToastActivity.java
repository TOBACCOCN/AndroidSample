package com.example.sample.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        Button simple = findViewById(R.id.simple);
        simple.setOnClickListener(v -> {
            Toast.makeText(this, "SimpleToast", Toast.LENGTH_LONG).show();
        });

        Button normal = findViewById(R.id.normal);
        normal.setOnClickListener(v -> {
            LinearLayout linearLayout =new LinearLayout(ToastActivity.this);

            ImageView imageView = new ImageView(ToastActivity.this);
            imageView.setImageResource(R.drawable.libai);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            linearLayout.addView(imageView);

            TextView textView =new TextView(ToastActivity.this);
            textView.setText("libai");
            textView.setTextSize(40);
            linearLayout.addView(textView);

            Toast toast =new Toast(ToastActivity.this);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            // toast.setView(linearLayout);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });
    }
}