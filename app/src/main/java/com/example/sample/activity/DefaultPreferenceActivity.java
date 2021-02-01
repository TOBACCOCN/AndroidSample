package com.example.sample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;

import com.example.sample.R;

public class DefaultPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_preference);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DefaultFragment()).commit();
    }

    // this class must be public
    public static class DefaultFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.default_preference, rootKey);
            findPreference("ROM").setSummary("128G");
            findPreference("RAM").setSummary("4G");
        }
    }

}
