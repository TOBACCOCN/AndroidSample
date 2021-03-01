package com.example.sample.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SimpleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view);

        ConstraintLayout root = findViewById(R.id.root);
        SimpleView view = new SimpleView(this);
        root.addView(view);
    }

    static class SimpleView extends View {

        // ANTI_ALIAS_FLAG 抗锯齿
        // 或者 setAntiAlias(true);
        private final Paint mPaint = new Paint(/*Paint.ANTI_ALIAS_FLAG*/);

        public SimpleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // // circle
            // canvas.drawCircle(300, 300, 200, mPaint);
            // mPaint.setColor(Color.BLUE);
            // mPaint.setStyle(Paint.Style.STROKE);
            // mPaint.setStrokeWidth(20);
            // canvas.drawCircle(800, 300, 200, mPaint);
            //
            // // rectangle
            // canvas.drawRect(100, 600, 500, 1000, mPaint);
            // mPaint.setColor(Color.BLACK);   // BLACK 为默认颜色
            // mPaint.setStyle(Paint.Style.FILL);  // FILL 为默认 style
            // canvas.drawRect(600, 600, 1000, 1000, mPaint);
            //
            // // point
            // mPaint.setStyle(Paint.Style.STROKE);
            // mPaint.setStrokeWidth(20);
            // // mPaint.setStrokeCap(Paint.Cap.BUTT); // default
            // mPaint.setStrokeCap(Paint.Cap.SQUARE);
            // canvas.drawPoint(300, 1100, mPaint);
            // mPaint.setStrokeCap(Paint.Cap.ROUND);
            // float[] pts = new float[]{0, 0, 700, 1100, 900, 1100, 700, 1200, 900, 1200};
            // canvas.drawPoints(pts, 2, 8, mPaint);
            //
            // // oval
            // canvas.drawOval(100, 1300, 500, 1500, mPaint);
            // mPaint.setStyle(Paint.Style.FILL);
            // mPaint.setColor(Color.BLUE);
            // canvas.drawOval(600, 1300, 1000, 1500, mPaint);
            //
            // // line
            // mPaint.setStrokeCap(Paint.Cap.SQUARE);
            // canvas.drawLine(150, 1650, 450, 1950, mPaint);
            // pts = new float[]{600, 1600, 1000, 1600, 800, 1600, 800, 2000, 600, 2000, 1000, 2000};
            // mPaint.setColor(Color.BLACK);
            // canvas.drawLines(pts, mPaint);
            //
            // // roundRectangle
            // canvas.drawRoundRect(150, 650, 450, 950, 20, 20, mPaint);
            // mPaint.setColor(Color.BLUE);
            // mPaint.setStyle(Paint.Style.STROKE);
            // mPaint.setStrokeWidth(20);
            // canvas.drawRoundRect(650, 650, 950, 950, 20, 20, mPaint);

            mPaint.setColor(Color.RED);
            canvas.drawArc(100, 100, 500, 500, -110, 90, true, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawArc(100, 100, 500, 500, -10, 70, true, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawArc(100, 100, 500, 500, 70, 70, true, mPaint);
            mPaint.setColor(Color.YELLOW);
            canvas.drawArc(100, 100, 500, 500, 150, 90, true, mPaint);

            // path
            mPaint.setColor(Color.BLACK);
            Path path = new Path();
            path.addCircle(800, 300, 200, Path.Direction.CW);
            path.addCircle(850, 300, 200, Path.Direction.CW);
            // path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            path.setFillType(Path.FillType.EVEN_ODD);
            canvas.drawPath(path, mPaint);

            // color
            // canvas.drawColor(Color.parseColor("#88880000"));

            // LinearGradient 线性渐变
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;   // default
            // Shader.TileMode tileMode = Shader.TileMode.MIRROR;
            // Shader.TileMode tileMode = Shader.TileMode.REPEAT;
            // Shader shader = new LinearGradient(100, 600, 300, 800, Color.parseColor("#DB256D"), Color.parseColor("#2196F3"), tileMode);  // 此行可以展示不同 tileMode 的效果
            Shader shader = new LinearGradient(100, 600, 500, 1000, Color.parseColor("#DB256D"), Color.parseColor("#2196F3"), tileMode);
            mPaint.setShader(shader);
            canvas.drawCircle(300, 800, 200, mPaint);
            // RadialGradient 辐射渐变
            shader = new RadialGradient(800, 800, 200, Color.parseColor("#DB256D"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            canvas.drawCircle(800, 800, 200, mPaint);
            // SweepGradient 扫描渐变
            shader = new SweepGradient(300, 1300, Color.parseColor("#DB256D"), Color.parseColor("#2196F3"));
            mPaint.setShader(shader);
            canvas.drawCircle(300, 1300, 200, mPaint);
            // SweepGradient 扫描渐变
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dufu);
            shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            canvas.drawCircle(800, 1300, 200, mPaint);

            // Bézier curve
            mPaint.setShader(null);
            mPaint.setStyle(Paint.Style.STROKE);
            path.moveTo(100, 1600);
            path.quadTo(100, 2000, 500, 1600);      // 似乎需要移除抗锯齿线条才比较顺滑
            canvas.drawPath(path, mPaint);
            path.moveTo(600, 1600);
            path.cubicTo(600, 2000, 1000, 2000, 1000, 1600);
            canvas.drawPath(path, mPaint);

            // bitmap
            canvas.save();
            canvas.clipRect(100, 1900, 200, 2000);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wcdb);
            mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
            canvas.drawBitmap(bitmap, 100, 1900, mPaint);
            canvas.restore();

            // text
            mPaint.setMaskFilter(null);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(36);
            canvas.drawText("Hello canvas", 600, 2100, mPaint);
        }
    }
}