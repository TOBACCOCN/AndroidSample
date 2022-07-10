package com.example.sample.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private ImageView mImageView;
    private float mCurrentDegree = 0.0f;
    private float[] gravities;
    private float[] magnetics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mImageView = findViewById(R.id.image);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        // mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String type = event.sensor.getStringType();
        // XLog.d("onSensorChanged, type: [%s]", type);
        if (Sensor.STRING_TYPE_ACCELEROMETER.equals(type)) {
            gravities = event.values;
        }
        if (Sensor.STRING_TYPE_MAGNETIC_FIELD.equals(type)) {
            magnetics = event.values;
        }
        calculateDegree();
        // if (Sensor.STRING_TYPE_ORIENTATION.equals(type)) {
        //     float degree = event.values[0];
        //     RotateAnimation animation = new RotateAnimation(mCurrentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //     animation.setDuration(200);
        //     mImageView.startAnimation(animation);
        //     mCurrentDegree = -degree;
        // }
    }

    private void calculateDegree() {
        float[] floats = new float[9];
        SensorManager.getRotationMatrix(floats, null, gravities, magnetics);
        float[] values = new float[3];
        SensorManager.getOrientation(floats, values);
        RotateAnimation animation = new RotateAnimation(mCurrentDegree, -values[0], Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        mImageView.startAnimation(animation);
        mCurrentDegree = -values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}