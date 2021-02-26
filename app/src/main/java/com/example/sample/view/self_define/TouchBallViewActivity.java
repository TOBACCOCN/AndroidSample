package com.example.sample.view.self_define;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.sample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TouchBallViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);

        ConstraintLayout root = findViewById(R.id.ball);
        TouchBallView view = new TouchBallView(this);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
        root.addView(view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    static class TouchBallView extends View {

        private float currentX = 40;
        private float currentY = 50;
        private final Paint paint = new Paint();

        public TouchBallView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(Color.RED);
            canvas.drawCircle(currentX, currentY, 15, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            currentX = event.getX();
            currentY = event.getY();
            // 通知重绘
            invalidate();
            return true;
        }
    }
}