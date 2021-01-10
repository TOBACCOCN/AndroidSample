package com.example.sample.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        View root = getLayoutInflater().inflate(R.layout.login, null);
        popupWindow = new PopupWindow(root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void showPopup(View view) {
        popupWindow.showAtLocation(findViewById(R.id.button), Gravity.CENTER, 0, 600);
    }

    public void closePopup(View view) {
        popupWindow.dismiss();
    }
}