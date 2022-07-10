package com.example.sample.sensor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.sample.R;

import androidx.annotation.Nullable;

public class LevelerView extends View {

    private float width;
    private Bitmap back;
    private Bitmap bubble;
    private float bubbleX;
    private float bubbleY;

    public Bitmap getBack() {
        return back;
    }

    public void setBack(Bitmap back) {
        this.back = back;
    }

    public Bitmap getBubble() {
        return bubble;
    }

    public void setBubble(Bitmap bubble) {
        this.bubble = bubble;
    }

    public float getBubbleX() {
        return bubbleX;
    }

    public void setBubbleX(float bubbleX) {
        this.bubbleX = bubbleX;
    }

    public float getBubbleY() {
        return bubbleY;
    }

    public void setBubbleY(float bubbleY) {
        this.bubbleY = bubbleY;
    }

    public LevelerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getDisplay().getRealMetrics(metrics);
        } else {
            // windowManager.getDefaultDisplay().getMetrics(metrics);
        }

        width = metrics.widthPixels;
        back = Bitmap.createBitmap((int) width, (int) width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(back);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        LinearGradient linearGradient = new LinearGradient(0f, width, (float) (width * 0.8), (float) (width * 0.2), Color.YELLOW, Color.WHITE, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(5);
        paint1.setColor(Color.BLACK);
        canvas.drawCircle(width / 2, width / 2, width / 2, paint1);
        canvas.drawLine(0f, width / 2, width, width / 2, paint1);
        canvas.drawLine(width / 2, 0, width / 2, width, paint1);

        Paint paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setStrokeWidth(10);
        canvas.drawLine(width / 2 - 30, width / 2, width / 2 + 30, width / 2, paint2);
        canvas.drawLine(width / 2, width / 2 - 30, width / 2, width / 2 + 30, paint2);

        bubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);
        // bubbleX = bubbleY = width / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(back, 0f, 0f, null);
        // canvas.drawCircle(width / 2, width / 2, width / 2, null);
        // canvas.drawLine(0f, width / 2, width, width / 2, null);
        // canvas.drawLine(width / 2, 0, width / 2, width, null);
        // canvas.drawLine(width / 2 - 30, width / 2, width / 2 + 30, width / 2, null);
        // canvas.drawLine(width / 2, width / 2 - 30, width / 2, width / 2 + 30, null);
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }
}