package com.example.sample.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class DrawViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);

        LinearLayout linearLayout = findViewById(R.id.root);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        linearLayout.addView(new DrawView(this, metrics.widthPixels, metrics.heightPixels));
    }

    public static class DrawView extends View {

        private Path mPath;
        private Canvas mCacheCanvas;
        private Paint mPaint;
        private Paint mBmpPaint = new Paint();
        private Bitmap mCacheBitmap;

        public DrawView(Context context, int width, int height) {
            super(context);
            mPath = new Path();
            mCacheCanvas = new Canvas();
            mCacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCacheCanvas.setBitmap(mCacheBitmap);
            mPaint = new Paint(Paint.DITHER_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    mCacheCanvas.drawPath(mPath, mPaint);
                    mPath.reset();
                    break;
                default:
                    break;
            }
            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // super.onDraw(canvas);
            canvas.drawBitmap(mCacheBitmap, 0, 0, mBmpPaint);
            canvas.drawPath(mPath, mPaint);
        }
    }
}