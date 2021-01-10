package com.example.sample.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import com.example.sample.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ContextMenuActivity extends AppCompatActivity {

    private TextView textView;

    private static final int FONT_SIZE_10 = 10;
    private static final int FONT_SIZE_11 = 11;
    private static final int FONT_SIZE_12 = 12;
    private static final int FONT_SIZE_13 = 13;
    private static final int NORMAL_MENU = 14;
    private static final int FONT_COLOR_RED = 15;
    private static final int FONT_COLOR_GREEN = 16;
    private static final int FONT_COLOR_BLUE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = findViewById(R.id.tv);
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        SubMenu fontSizeMenu = menu.addSubMenu("字体大小");
        fontSizeMenu.setIcon(android.R.drawable.star_big_on);   // no effect
        fontSizeMenu.setHeaderIcon(android.R.drawable.btn_radio);
        fontSizeMenu.setHeaderTitle("选择字体大小");
        fontSizeMenu.add(0, FONT_SIZE_10, 0, "10 号字体");
        fontSizeMenu.add(0, FONT_SIZE_11, 0, "11 号字体");
        fontSizeMenu.add(0, FONT_SIZE_12, 0, "12 号字体");
        fontSizeMenu.add(0, FONT_SIZE_13, 0, "13 号字体");

        menu.add(1, NORMAL_MENU, 0, "普通菜单项");

        SubMenu fontColorMenu = menu.addSubMenu("字体颜色");
        fontColorMenu.setIcon(android.R.drawable.star_big_on);  // no effect
        fontColorMenu.setHeaderIcon(android.R.drawable.btn_radio);
        fontColorMenu.setHeaderTitle("选择字体字体颜色");
        fontColorMenu.add(2, FONT_COLOR_RED, 0, "红色");
        fontColorMenu.add(2, FONT_COLOR_GREEN, 0, "绿色");
        fontColorMenu.add(2, FONT_COLOR_BLUE, 0, "蓝色");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case FONT_SIZE_10:
                textView.setTextSize(10*2);
                break;
            case FONT_SIZE_11:
                textView.setTextSize(11*2);
                break;
            case FONT_SIZE_12:
                textView.setTextSize(12*2);
                break;
            case FONT_SIZE_13:
                textView.setTextSize(13*2);
                break;
            case FONT_COLOR_RED:
                textView.setTextColor(Color.RED);
                break;
            case FONT_COLOR_GREEN:
                textView.setTextColor(Color.GREEN);
                break;
            case FONT_COLOR_BLUE:
                textView.setTextColor(Color.BLUE);
                break;
            default:
                break;
        }
        return true;
    }
}