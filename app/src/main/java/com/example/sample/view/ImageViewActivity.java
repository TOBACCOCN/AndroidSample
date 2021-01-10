package com.example.sample.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ImageViewActivity extends AppCompatActivity {

    private final int[] images = new int[]{R.drawable.libai, R.drawable.dufu, R.drawable.baijuyi, R.drawable.dumu, R.drawable.lishangyin, R.drawable.luyou};
    private int currentImage = 0;
    private int alpha = 255;
    private final List<String> permissions = new ArrayList<>(Arrays.asList(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 0x11);

        ImageView imageView1 = findViewById(R.id.iv1);
        imageView1.setImageResource(images[currentImage]);

        Button minusBtn = findViewById(R.id.minus);
        Button plusBtn = findViewById(R.id.plus);
        View.OnClickListener listener = v -> {
            if (v == minusBtn) {
                alpha -= 20;
            }
            if (v == plusBtn) {
                alpha += 20;
            }

            if (alpha >= 255) {
                alpha = 255;
            }
            if (alpha <= 0) {
                alpha = 0;
            }
            imageView1.setImageAlpha(alpha);
        };
        minusBtn.setOnClickListener(listener);
        plusBtn.setOnClickListener(listener);

        Button nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(v ->
                imageView1.setImageResource(images[++currentImage % images.length])
        );

        ImageView imageView2 = findViewById(R.id.iv2);
        imageView1.setOnTouchListener((v, event) -> {
            BitmapDrawable drawable = (BitmapDrawable) imageView1.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            int x = (int) event.getX();
            if (x + 120 > bitmap.getWidth()) {
                x = bitmap.getWidth() - 120;
            }
            int y = (int) event.getY();
            if (y + 120 > bitmap.getHeight()) {
                y = bitmap.getHeight() - 120;
            }
            Bitmap bm = Bitmap.createBitmap(bitmap, x, y, 120, 120);
            imageView2.setImageBitmap(bm);
            return false;
        });

        QuickContactBadge badge = findViewById(R.id.quickContact);
        badge.assignContactFromPhone("15907162030", false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x11) {
            Integer[] grants = new Integer[grantResults.length];
            for (int i = 0; i < grantResults.length; ++i) {
                grants[i] = grantResults[i];
            }
            if (!new ArrayList<>(Arrays.asList(permissions)).containsAll(this.permissions) || Arrays.asList(grants).contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(this, "Nothing can be used", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}