package com.example.sample.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class LevelerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private LevelerView mLevelerView;
    private TextView mOrientationTextView;
    private float[] gravities;
    private float[] magnetics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leveler);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLevelerView = findViewById(R.id.leveler);
        mOrientationTextView = findViewById(R.id.orientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        // mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String type = event.sensor.getStringType();
        if (Sensor.STRING_TYPE_ACCELEROMETER.equals(type)) {
            gravities = event.values;
        }
        if (Sensor.STRING_TYPE_MAGNETIC_FIELD.equals(type)) {
            magnetics = event.values;
        }
        calculateDegree();
        // if (Sensor.STRING_TYPE_ORIENTATION.equals(type)) {
        //     float xDegree = event.values[1];
        //     float yDegree = event.values[2];
        //     int distance = mLevelerView.getBack().getWidth() / 2 - mLevelerView.getBubble().getWidth() / 2;
        //     double sinx = Math.sin(xDegree * Math.PI / 180);
        //     double siny = Math.sin(yDegree * Math.PI / 180);
        //     String text = "绕 Z 轴方向旋转角度为：" + event.values[0] + "\n绕 X 轴方向旋转角度为：" + xDegree + "\n绕 Y 轴方向旋转角度为：" + yDegree + "\nsinx: "+ sinx + "\nsiny: " + siny;
        //     mOrientationTextView.setText(text);
        //     mLevelerView.setBubbleY((float) (mLevelerView.getBack().getWidth() / 2 -mLevelerView.getBubble().getWidth() / 2+ distance*Math.sin(xDegree * Math.PI / 180)));
        //     mLevelerView.setBubbleX((float) (mLevelerView.getBack().getWidth() / 2 -mLevelerView.getBubble().getWidth() / 2 + distance*Math.sin(yDegree * Math.PI / 180)));
        //     mLevelerView.postInvalidate();
        // }
    }

    private void calculateDegree() {
        float[] floats = new float[9];
        SensorManager.getRotationMatrix(floats, null, gravities, magnetics);
        float[] values = new float[3];
        SensorManager.getOrientation(floats, values);
        float xDegree = values[1];
        float yDegree = values[2];
        int distance = mLevelerView.getBack().getWidth() / 2 - mLevelerView.getBubble().getWidth() / 2;
        double sinx = Math.sin(xDegree * Math.PI / 180);
        double siny = Math.sin(yDegree * Math.PI / 180);
        String text = "绕 Z 轴方向旋转角度为：" + values[0] + "\n绕 X 轴方向旋转角度为：" + xDegree + "\n绕 Y 轴方向旋转角度为：" + yDegree + "\nsinx: " + sinx + "\nsiny: " + siny;
        mOrientationTextView.setText(text);
        mLevelerView.setBubbleY((float) (mLevelerView.getBack().getWidth() / 2 - mLevelerView.getBubble().getWidth() / 2 + distance * Math.sin(xDegree * Math.PI / 180)));
        mLevelerView.setBubbleX((float) (mLevelerView.getBack().getWidth() / 2 - mLevelerView.getBubble().getWidth() / 2 + distance * Math.sin(yDegree * Math.PI / 180)));
        mLevelerView.postInvalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}