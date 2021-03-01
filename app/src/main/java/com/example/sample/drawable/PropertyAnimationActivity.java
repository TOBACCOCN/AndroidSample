package com.example.sample.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class PropertyAnimationActivity extends AppCompatActivity {

    private TextView mAlphaTextView;
    private TextView mTransitionTextView;
    private TextView mRotateTextView;
    private TextView mTransitionRotateAlphaTextView;
    private TextView mTransitionRotateAlphaXmlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        mAlphaTextView = findViewById(R.id.alpha);
        mTransitionTextView = findViewById(R.id.transition);
        mRotateTextView = findViewById(R.id.rotate);
        mTransitionRotateAlphaTextView = findViewById(R.id.transition_rotate_alpha);
        mTransitionRotateAlphaXmlTextView = findViewById(R.id.transition_rotate_alpha_xml);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mAlphaTextView, "alpha", 1.0f, 0.0f, 1.0f);
        animator.setDuration(3000);
        animator.start();

        float translationX = mTransitionTextView.getTranslationX();
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        animator = ObjectAnimator.ofFloat(mTransitionTextView, "translationX", translationX,
                (float) (metrics.widthPixels - mTransitionTextView.getWidth() - 178) / 2, translationX);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());     // default，先加速再减速
        // animator.setInterpolator(new LinearInterpolator());     // 匀速
        animator.setDuration(8000);
        animator.start();

        animator = ObjectAnimator.ofFloat(mRotateTextView, "rotation", 0, 360);
        animator.setDuration(6000);
        animator.start();

        Animator translation = ObjectAnimator.ofFloat(mTransitionRotateAlphaTextView, "translationX", translationX,
                (float) (metrics.widthPixels - mTransitionRotateAlphaTextView.getWidth() - 400) / 2, translationX);
        Animator rotate = ObjectAnimator.ofFloat(mTransitionRotateAlphaTextView, "rotation", 0, 360);
        Animator alpha = ObjectAnimator.ofFloat(mTransitionRotateAlphaTextView, "alpha", 1.0f, 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate).with(alpha).after(translation);
        animatorSet.setDuration(6000);
        animatorSet.start();

        Animator animatorXml = AnimatorInflater.loadAnimator(this, R.animator.simple_animation);
        animatorXml.setTarget(mTransitionRotateAlphaXmlTextView);
        animatorXml.start();
    }
}
