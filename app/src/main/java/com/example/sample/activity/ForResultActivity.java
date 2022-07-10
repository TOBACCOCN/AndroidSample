package com.example.sample.activity;

import android.content.Intent;
import android.os.Bundle;

import com.elvishew.xlog.XLog;
import com.example.sample.R;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ForResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_result);
        Intent intent = new Intent(this, SimpleExpandableListActivity.class);
        ActivityResultLauncher<Intent> register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();
            if (data != null) {
                XLog.i("armType: [%s], arm: [%s]", data.getExtras().getString("armType"), data.getExtras().getString("arm"));
            }
        });
        register.launch(intent);
        // startActivityForResult(intent, 0x123);
    }

    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //     if (requestCode == 0x123 && resultCode == 0x123) {
    //         XLog.i("armType: [%s], arm: [%s]", data.getExtras().getString("armType"), data.getExtras().getString("arm"));
    //     }
    //     super.onActivityResult(requestCode, resultCode, data);
    // }
}