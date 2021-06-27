package com.example.sample.gesture;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class GestureZoomActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;
    private float mCurrentScale = 1.0f;
    // private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_gesture_zoom);
        MyImageView myImageView = new MyImageView(this, R.drawable.dufu);
        setContentView(myImageView);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                XLog.d("velocityX: [%f]", velocityX);
                velocityX = velocityX > 4000 || velocityX < -4000 ? velocityX : 4000;
                XLog.d("after calc, velocityX: [%f]", velocityX);
                float scale = velocityX > 0 ?  velocityX / 4000 : -4000/velocityX;
                XLog.d("scale: [%f]", scale);
                mCurrentScale *= scale;
                // mCurrentScale = mCurrentScale <= 0 ? 0.01f : mCurrentScale;
                // mCurrentScale = mCurrentScale >=2 ? 2 : mCurrentScale;
                if (mCurrentScale != 1) {
                    myImageView.getMatrix().setScale(mCurrentScale, mCurrentScale);
                    myImageView.postInvalidate();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}