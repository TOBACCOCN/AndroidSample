package com.example.sample.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {

    private Bitmap mBitmap;
    private Matrix mMatrix = new Matrix();

    public Matrix getMatrix() {
        return mMatrix;
    }

    public MyImageView(@NonNull Context context, int resId) {
        super(context);
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
        mMatrix.setScale(1.0f, 1.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
