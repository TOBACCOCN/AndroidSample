package com.example.sample.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.sample.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogActivity extends AppCompatActivity {
// public class AlertDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this).setTitle("AlertDialog")
                    .setIcon(R.drawable.ic_launcher);

            // 1.带确定取消按钮的 dialog
            // builder.setMessage("Hello from AlertDialog");
            // setPositiveButton(builder, null, null);
            // setNegativeButton(builder);

            // 2.单选列表 dialog
            // setItems(builder);

            // 多选列表与自定义列表需要用到 items 变量
            // String[] items = new String[]{"疯狂 Android 讲义", "代码整洁之道", "程序员修炼之道", "深入理解 Java 虚拟机"};

            // 3.多选列表 dialog
            // boolean[] booleans = new boolean[]{true, false, false, true};
            // setMultiChoiceItems(builder, items, booleans);
            // setPositiveButton(builder, items, booleans);
            // setNegativeButton(builder);

            // 4.自定义列表项 dialog
            // builder.setAdapter(new ArrayAdapter<>(AlertDialogActivity.this, R.layout.array_item, R.id.tv, items), (dialog, which) ->
            //         Toast.makeText(AlertDialogActivity.this, items[which], Toast.LENGTH_LONG).show()
            // );

            // 5.自定义 View
            TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.login, null);
            builder.setView(tableLayout)
            .setPositiveButton("登录", (dialog, which) -> {
                EditText user = tableLayout.findViewById(R.id.user);
                EditText password = tableLayout.findViewById(R.id.password);
                Toast.makeText(AlertDialogActivity.this, "账号: " + user.getText() + ", 密码: " + password.getText(), Toast.LENGTH_LONG).show();
            }).setNegativeButton("取消", (dialog, which) -> Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_LONG).show());


            builder.create().show();

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    private void setMultiChoiceItems(AlertDialog.Builder builder, String[] items, boolean[] booleans) {
        builder.setMultiChoiceItems(items, booleans, (dialog, which, isChecked) -> booleans[which] = isChecked);
    }

    private void setItems(AlertDialog.Builder builder) {
        String[] items = new String[]{"疯狂 Android 讲义", "代码整洁之道"};
        builder.setItems(items, (dialog, which) -> Toast.makeText(AlertDialogActivity.this, items[which], Toast.LENGTH_LONG).show());
    }

    private void setNegativeButton(AlertDialog.Builder builder) {
        builder.setNegativeButton("取消", (dialog, which) -> Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_LONG).show());
    }

    private void setPositiveButton(AlertDialog.Builder builder, String[] items, boolean[] booleans) {
        builder.setPositiveButton("确定", (dialog, which) -> {
            if (items == null || booleans == null) {
                Toast.makeText(AlertDialogActivity.this, "确定", Toast.LENGTH_LONG).show();
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < booleans.length; ++i) {
                stringBuilder.append(booleans[i] ? items[i] + ", " : "");
            }
            String choosed = stringBuilder.length() > 0 ? stringBuilder.substring(0, stringBuilder.length() - 2) : "";
            Toast.makeText(AlertDialogActivity.this, choosed, Toast.LENGTH_LONG).show();
        });
    }
}