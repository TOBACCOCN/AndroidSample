package com.example.sample.gesture;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class GestureActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        XLog.d("onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        XLog.d("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        XLog.d("onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        XLog.d("onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        XLog.d("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        XLog.d("onFling");
        return false;
    }
}