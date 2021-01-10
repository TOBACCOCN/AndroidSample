package com.example.sample.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class PopupMenuActivity extends AppCompatActivity {

    private TextView textView;

    private static final int FONT_SIZE_10 = 10;
    private static final int FONT_SIZE_11 = 11;
    private static final int FONT_SIZE_12 = 12;
    private static final int FONT_SIZE_13 = 13;
    private static final int FONT_COLOR_RED = 15;
    private static final int FONT_COLOR_GREEN = 16;
    private static final int FONT_COLOR_BLUE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_menu);
        textView = findViewById(R.id.tv);
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.simple_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()) {
                case FONT_SIZE_10:  // java
                case R.id.size10:  // xml
                    textView.setTextSize(10*2);
                    break;
                case FONT_SIZE_11:  // java
                case R.id.size11:  // xml
                    textView.setTextSize(11*2);
                    break;
                case FONT_SIZE_12:  // java
                case R.id.size12:  // xml
                    textView.setTextSize(12*2);
                    break;
                case FONT_SIZE_13:  // java
                case R.id.size13:  // xml
                    textView.setTextSize(13*2);
                    break;
                case FONT_COLOR_RED:  // java
                case R.id.color15:  // xml
                    textView.setTextColor(Color.RED);
                    break;
                case FONT_COLOR_GREEN:  // java
                case R.id.color16:  // xml
                    textView.setTextColor(Color.GREEN);
                    break;
                case FONT_COLOR_BLUE:  // java
                case R.id.color17:  // xml
                    textView.setTextColor(Color.BLUE);
                    break;
                default:
                    break;
            }
            return true;
        });
        popupMenu.show();
    }
}