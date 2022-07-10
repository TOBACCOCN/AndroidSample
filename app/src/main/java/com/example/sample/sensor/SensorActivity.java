package com.example.sample.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private TextView mOrientationTextView;
    private TextView mAccelerometerTextView;
    private TextView mGravityTextView;
    private TextView mAccelerationTextView;
    private TextView mGyroscopeTextView;
    private TextView mMagneticTextView;
    private TextView mLightTextView;
    // private TextView mTemperatureTextView;
    // private TextView mHumidityTextView;
    private TextView mPressureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        XLog.d("SENSOR_SIZE: [%d]", sensorList.size());
        for (Sensor sensor : sensorList) {
            XLog.d("NAME: [%s]", sensor.getName());
            XLog.d("TYPE: [%s]", sensor.getStringType());
            XLog.d("VENDOR: [%s]", sensor.getVendor());
            XLog.d("POWER: [%s]", sensor.getPower());
            XLog.d("RESOLUTION: [%s]", sensor.getResolution());
        }

        mOrientationTextView = findViewById(R.id.orientation);
        mAccelerometerTextView = findViewById(R.id.accelerometer);
        mGravityTextView = findViewById(R.id.gravity);
        mAccelerationTextView = findViewById(R.id.linear_acceleration);
        mGyroscopeTextView = findViewById(R.id.gyroscope);
        mMagneticTextView = findViewById(R.id.magnetic);
        mLightTextView = findViewById(R.id.light);
        // mTemperatureTextView = findViewById(R.id.temperature);
        // mHumidityTextView = findViewById(R.id.humidity);
        mPressureTextView = findViewById(R.id.pressure);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        // 当前使用手机没有温度传感器和湿度传感器
        // mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        // mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String type = event.sensor.getStringType();
        // XLog.i("onSensorChanged, type: [%s]", type);
        float[] values = event.values;
        String text;
        switch (type) {
            // case Sensor.STRING_TYPE_ORIENTATION:
            //     text = "绕 Z 轴方向旋转角度为：" + values[0] + "\n绕 X 轴方向旋转角度为：" + values[1] + "\n绕 Y 轴方向旋转角度为：" + values[2];
            //     mOrientationTextView.setText(text);
            //     break;
            case Sensor.STRING_TYPE_ACCELEROMETER:
                text = "X 轴方向加速度为：" + values[0] + "\nY 轴方向加速度为：" + values[1] + "\nZ 轴方向加速度为：" + values[2];
                mAccelerometerTextView.setText(text);
                break;
            case Sensor.STRING_TYPE_GRAVITY:
                text = "X 轴方向重力为：" + values[0] + "\nY 轴方向重力为：" + values[1] + "\nZ 轴方向重力为：" + values[2];
                mGravityTextView.setText(text);
                break;
            case Sensor.STRING_TYPE_LINEAR_ACCELERATION:
                text = "X 轴方向线性加速度为：" + values[0] + "\nY 轴方向线性加速度为：" + values[1] + "\nZ 轴方向线性加速度为：" + values[2];
                mAccelerationTextView.setText(text);
                break;
            case Sensor.STRING_TYPE_GYROSCOPE:
                text = "绕 X 轴方向旋转角速度为：" + values[0] + "\n绕 Y 轴方向旋转角速度为：" + values[1] + "\n绕 Z 轴方向旋转角速度为：" + values[2];
                mGyroscopeTextView.setText(text);
                break;
            case Sensor.STRING_TYPE_MAGNETIC_FIELD:
                text = "X 轴方向磁场强度为：" + values[0] + "\nY 轴方向磁场强度为：" + values[1] + "\nZ 轴方向磁场强度为：" + values[2];
                mMagneticTextView.setText(text);
                break;
            case Sensor.STRING_TYPE_LIGHT:
                text = "当前光照强度为：" + values[0];
                mLightTextView.setText(text);
                break;
            case Sensor.STRING_TYPE_PRESSURE:
                text = "当前压力为：" + values[0];
                mPressureTextView.setText(text);
                break;

            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}