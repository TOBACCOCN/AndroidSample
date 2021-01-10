package com.example.sample.event;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PlaneEventActivity extends AppCompatActivity{

    private int width;
    private int height;
    private int speed = 10;
    private PlaneView planeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // setContentView(R.layout.activity_plane_event);
        getSupportActionBar().hide();

        Handler handler =new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 123) {
                    planeView.invalidate();
                }
            }
        };

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        planeView = new PlaneView(this, handler);
        planeView.currentX = width/ 2;
        planeView.currentY = height - 200;
        // ConstraintLayout root = findViewById(R.id.root);
        // root.addView(planeView);
        setContentView(planeView);

        planeView.setOnTouchListener((v, event) -> {
            float x = event.getX();
            if (x < width / 8) {
                planeView.currentX -= speed;
            }
            if (x > width - width / 8) {
                planeView.currentX += speed;
            }

            float y = event.getY();
            if (y < height / 8) {
                planeView.currentY -= speed;
            }
            if (y > height - height / 8) {
                planeView.currentY += speed;
            }

            return true;
        });

        Configuration configuration = getResources().getConfiguration();
        int navigation = configuration.navigation;
        int orientation = configuration.orientation;
        int mcc = configuration.mcc;
        int mnc = configuration.mnc;
        int touchscreen = configuration.touchscreen;
        XLog.i("NAVIGATION: [%s]", navigation == Configuration.NAVIGATION_NONAV ? "NONAV": "HAS_NAV");
        XLog.i("ORIENTATION: [%s]", orientation == Configuration.ORIENTATION_LANDSCAPE ? "LANDSCAPE" : "PORTRAIT");
        XLog.i("mcc: [%d]", mcc);
        XLog.i("mnc: [%d]", mnc);
        XLog.i("touchscreen: [%s]", touchscreen == Configuration.TOUCHSCREEN_NOTOUCH ? "no" : "yes");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class PlaneView extends View {

        private int currentX;
        private int currentY;
        private Bitmap plane0;
        private Bitmap plane1;
        private int index;

        public PlaneView(Context context, Handler handler) {
            super(context);
            plane0 = BitmapFactory.decodeResource(getResources(), R.drawable.plane0);
            plane1 = BitmapFactory.decodeResource(getResources(), R.drawable.plane1);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ++index;
                   handler.sendEmptyMessage(123);
                }
            }, 0, 100);
            setFocusable(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap((index & 1) == 0 ? plane0 : plane1, currentX, currentY, new Paint());
        }
    }
}