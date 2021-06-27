package com.example.sample.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.elvishew.xlog.XLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class GestureMultiZoomTextView extends AppCompatTextView {

    private double mPreDistance;
    private float mTextSize;

    public GestureMultiZoomTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        if (pointerCount >= 2) {
            if (mTextSize == 0.0f) {
                mTextSize = getTextSize();
            }
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    mPreDistance = calcDistance(event);
                    XLog.d("preDistance: [%d]", mPreDistance);
                    break;
                case MotionEvent.ACTION_MOVE:
                    double curDistance = calcDistance(event);
                    XLog.d("curDistance: [%d]", curDistance);
                    zoom(mPreDistance / curDistance);
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void zoom(double zoom) {
        XLog.d("zoom: [%d]", zoom);
        this.setTextSize(px2sp(getContext(), mTextSize * zoom));
    }

    private float px2sp(Context context, double textSize) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (float) (scaledDensity * textSize);
    }

    private double calcDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }
}
