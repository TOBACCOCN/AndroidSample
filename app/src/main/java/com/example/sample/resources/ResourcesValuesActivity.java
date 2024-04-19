package com.example.sample.resources;

import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import java.util.stream.Stream;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ResourcesValuesActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_value);

        XLog.d("STRING: [%s]", getString(R.string.string));
        XLog.d("INTEGER: [%s]", getResources().getInteger(R.integer.integer));
        XLog.d("BOOL: [%s]", getResources().getBoolean(R.bool.bool_false));
        XLog.d("COLOR: [%s]", getResources().getColor(R.color.color_red, null));
        XLog.d("DIMEN: [%s]", getResources().getDimension(R.dimen.dimen_20));
        String[] stringArray = getResources().getStringArray(R.array.string_array);
        Stream.of(stringArray).forEach(string -> XLog.d("ARRAY_STRING: [%s]", string));
        int[] intArray = getResources().getIntArray(R.array.integer_array);
        for (int i : intArray) {
            XLog.d("ARRAY_INTEGER: [%d]", i);
        }
        TypedArray typedArray = getResources().obtainTypedArray(R.array.array);
        for (int i = 0; i < typedArray.length(); ++i) {
            // XLog.d("ARRAY_COLOR: [%d]", typedArray.getColor(i, 0xff0000));
            Log.d("TAG", String.format("ARRAY_COLOR: [{%d}]", typedArray.getColor(i, 0xff0000)));
        }
        for (int i = 0; i < typedArray.getIndexCount(); ++i) {
            XLog.d("ARRAY_COLOR: [%d]", typedArray.getColor(i, 0xff0000));
        }

    }

}