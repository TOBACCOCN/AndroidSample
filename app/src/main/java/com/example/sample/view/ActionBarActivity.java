package com.example.sample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sample.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActionBarActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        // 注意 AndroidManifest.xml 中的 app:theme 不可以为 NoActionBar
        // 且必须是 getSupportActionBar，getActionBar 可能为空
        actionBar = getSupportActionBar();
        // 左上角显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void hide(View view) {
        actionBar.hide();
    }

    public void show(View view) {
        actionBar.show();
    }

    public void gotoOptionMenu(View view) {
        Intent intent = new Intent(this, OptionsMenuActivity.class);
        startActivity(intent);
    }
}