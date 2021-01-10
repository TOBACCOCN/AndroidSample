package com.example.sample.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class TextSwitcherActivity extends AppCompatActivity {

    private TextSwitcher textSwitcher;
    private String[] roles = new String[]{"唐三藏", "孙悟空", "猪八戒", "沙悟净"};
    private int current = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switcher);

        textSwitcher = findViewById(R.id.switcher);
        textSwitcher.setFactory(() -> {
            TextView textView = new TextView(TextSwitcherActivity.this);
            textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(40);
            return textView;
        });
        next(null);
    }

    public void next(View view) {
        if (current < roles.length -1) {
            textSwitcher.setText(roles[++current]);
        }
    }
}