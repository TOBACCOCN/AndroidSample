package com.example.sample.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import com.example.sample.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OptionsMenuActivity extends AppCompatActivity {

    private TextView textView;

    private static final int FONT_SIZE_10 = 10;
    private static final int FONT_SIZE_11 = 11;
    private static final int FONT_SIZE_12 = 12;
    private static final int FONT_SIZE_13 = 13;
    private static final int BACK_TO_HOME = 14;
    private static final int FONT_COLOR_RED = 15;
    private static final int FONT_COLOR_GREEN = 16;
    private static final int FONT_COLOR_BLUE = 17;
    private static final int BOOK_JAVA = 18;
    private static final int BOOK_ANDROID = 19;
    private static final int BOOK_PYTHON = 20;

    private boolean javaChecked = true;
    private boolean androidChecked = true;
    private boolean pythonChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        textView = findViewById(R.id.tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 一、java 编码方式添加菜单
        // SubMenu fontSizeMenu = menu.addSubMenu("字体大小");
        // fontSizeMenu.setIcon(android.R.drawable.star_big_on);   // no effect
        // fontSizeMenu.setHeaderIcon(android.R.drawable.btn_radio);
        // fontSizeMenu.setHeaderTitle("选择字体大小");
        // fontSizeMenu.add(0, FONT_SIZE_10, 0, "10 号字体");
        // fontSizeMenu.add(0, FONT_SIZE_11, 0, "11 号字体");
        // fontSizeMenu.add(0, FONT_SIZE_12, 0, "12 号字体");
        // fontSizeMenu.add(0, FONT_SIZE_13, 0, "13 号字体");
        //
        // menu.add(1, NORMAL_MENU, 0, "普通菜单项");
        //
        // SubMenu fontColorMenu = menu.addSubMenu("字体颜色");
        // fontColorMenu.setIcon(android.R.drawable.star_big_on);  // no effect
        // fontColorMenu.setHeaderIcon(android.R.drawable.btn_radio);
        // fontColorMenu.setHeaderTitle("选择字体字体颜色");
        // fontColorMenu.add(2, FONT_COLOR_RED, 0, "红色");
        // fontColorMenu.add(2, FONT_COLOR_GREEN, 0, "绿色");
        // fontColorMenu.add(2, FONT_COLOR_BLUE, 0, "蓝色");

        // 二、XML 方式添加菜单
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.simple_menu, menu);

        menu.add(2, BACK_TO_HOME, 0,"回主页");

        SubMenu readBook = menu.addSubMenu("已读书籍");
        readBook.setHeaderIcon(android.R.drawable.btn_radio);
        readBook.setHeaderTitle("选择已读书籍");
        // 多选
        readBook.setGroupCheckable(3, true, false);
        // 单选
        // readBook.setGroupCheckable(3, true, true);
        MenuItem java = readBook.add(3, BOOK_JAVA, 0, "疯狂 Java 讲义");
        java.setCheckable(true);
        // java.setChecked(javaChecked);
        MenuItem android = readBook.add(3, BOOK_ANDROID, 0, "疯狂 Android 讲义");
        android.setCheckable(true);
        // android.setChecked(androidChecked);
        MenuItem python = readBook.add(3, BOOK_PYTHON, 0, "疯狂 Python 讲义");
        python.setCheckable(true);
        // python.setChecked(pythonChecked);

        SubMenu activity = menu.addSubMenu("打开页面");
        MenuItem button = activity.add("按钮页面");
        // 重写 onOptionsItemSelected 时 setIntent 无效
        button.setIntent(new Intent(this, ButtonActivity.class));
        MenuItem image = activity.add("图片页面");
        // 重写 onOptionsItemSelected 时 setIntent 无效
        image.setIntent(new Intent(this, ImageViewActivity.class));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
            case BACK_TO_HOME:  // java
                Intent intent = new Intent(this, ActionBarActivity.class);
                // 清除任务栈中 ActionBarActivity 之上的任务
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
            case BOOK_JAVA:
                javaChecked = !javaChecked;
                item.setChecked(javaChecked);
                break;
            case BOOK_ANDROID:
                androidChecked = !androidChecked;
                item.setChecked(androidChecked);
                break;
            case BOOK_PYTHON:
                pythonChecked = !pythonChecked;
                item.setChecked(pythonChecked);
                break;
            default:
                break;
        }
        return true;
    }
}