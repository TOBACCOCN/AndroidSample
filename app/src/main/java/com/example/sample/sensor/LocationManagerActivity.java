package com.example.sample.sensor;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample.R;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LocationManagerActivity extends AppCompatActivity {

    private TextView mTextView;

    private class ProximityAlertReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false)) {
                Toast.makeText(LocationManagerActivity.this, "您已进入 KEY_ZONE", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LocationManagerActivity.this, "您已离开 KEY_ZONE", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);

        mTextView = findViewById(R.id.tv);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        ListView listView = findViewById(R.id.list);
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.array_item, R.id.tv, providers);
        listView.setAdapter(adapter);

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0x11);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.01f, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateView(location);
            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(LocationManagerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(LocationManagerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                updateView(locationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                updateView(null);
            }
        });
        double latitude = 22.5215d, longitude = 113.9212d;
        float radius = 0.01f;
        PendingIntent pi = PendingIntent.getBroadcast(this, 0x12, new Intent(this, ProximityAlertReceiver.class), 0);
        locationManager.addProximityAlert(latitude, longitude, radius, -1, pi);
    }

    private void updateView(Location location) {
        mTextView.setText("经度：" + location.getLongitude() + "\n纬度：" + location.getLatitude() + "\n高度：" + location.getAltitude()
                + "\n方向：" + location.getBearing() + "\n速度：" + location.getSpeed());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (0x11 == requestCode) {
            Integer[] grants = new Integer[grantResults.length];
            for (int i = 0; i < grantResults.length; ++i) {
                grants[i] = grantResults[i];
            }
            if (Arrays.asList(grants).contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}