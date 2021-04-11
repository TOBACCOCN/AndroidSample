package com.example.sample.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class AnimationDrawableBlastActivity extends AppCompatActivity {

    private static final int BLAST_WIDTH = 240;
    private static final int BLAST_HEIGHT = 240;
    private Drawable mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_animation_drawable_blast);

        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);
        SimpleView simpleView = new SimpleView(this);
        simpleView.setBackgroundResource(R.drawable.blast);
        simpleView.setVisibility(View.INVISIBLE);
        frameLayout.addView(simpleView);
        frameLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float x = event.getX();
                float y = event.getY();
                simpleView.setLocation((int) x - BLAST_WIDTH / 2, (int) y - BLAST_HEIGHT / 2, (int) x + BLAST_WIDTH / 2, (int) y + BLAST_HEIGHT / 2);
                simpleView.setVisibility(View.VISIBLE);
                mBackground = simpleView.getBackground();
                if (mBackground instanceof AnimationDrawable) {
                    ((AnimationDrawable) mBackground).start();
                }
                MediaPlayer mediaPlayer = MediaPlayer.create(AnimationDrawableBlastActivity.this, R.raw.bomb);
                // try {
                //     mediaPlayer.prepare();
                // } catch (IOException e) {
                //     ErrorPrintUtil.printErrorMsg(e);
                // }
                mediaPlayer.start();
            }
            return true;
        });

    }

    public class SimpleView extends AppCompatImageView {

        public SimpleView(Context context) {
            super(context);
        }


        // @Override
        // public boolean onTouchEvent(MotionEvent event) {
        //     if (event.getAction() == MotionEvent.ACTION_DOWN) {
        //         float x = event.getX();
        //         float y = event.getY();
        //         setFrame((int) x - BLAST_WIDTH / 2, (int) y - BLAST_HEIGHT / 2, (int) x + BLAST_WIDTH / 2, (int) y + BLAST_HEIGHT / 2);
        //         setVisibility(View.VISIBLE);
        //         mBackground = getBackground();
        //         if (mBackground instanceof AnimationDrawable) {
        //             ((AnimationDrawable) mBackground).start();
        //         }
        //         MediaPlayer mediaPlayer = MediaPlayer.create(AnimationDrawableBlastActivity.this, R.raw.bomb);
        //         try {
        //             mediaPlayer.prepare();
        //         } catch (IOException e) {
        //             ErrorPrintUtil.printErrorMsg(e);
        //         }
        //         mediaPlayer.start();
        //     }
        //     return true;
        // }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            try {
                Field mCurFrame = AnimationDrawable.class.getDeclaredField("mCurFrame");
                mCurFrame.setAccessible(true);
                int cur = mCurFrame.getInt(mBackground);
                if (cur == ((AnimationDrawable) mBackground).getNumberOfFrames() - 1) {
                    setVisibility(View.INVISIBLE);
                }

            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(e);
            }
        }

        public void setLocation(int l, int t, int r, int b) {
            setFrame(l, t, r, b);
        }
    }
}