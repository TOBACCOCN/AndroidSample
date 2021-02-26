package com.example.sample.resources;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimpleTextView extends View {

    private Paint mPaint;
    private String mText;

    public SimpleTextView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public SimpleTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleTextView);
        int color = typedArray.getColor(R.styleable.SimpleTextView_simpleColor, 0x000000);
        float size = typedArray.getFloat(R.styleable.SimpleTextView_simpleSize, 20);
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        mText = typedArray.getString(R.styleable.SimpleTextView_simpleText);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText, 100, 100, mPaint);
    }
}