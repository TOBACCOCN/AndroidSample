package com.example.sample.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class ViewSwitcherActivity extends AppCompatActivity {

    private ViewSwitcher viewSwitcher;
    private int screenNO = -1;
    private static final int COUNT_PER_SCREEN = 2;
    private int screenCount;
    private int appCount = 6;
    private AppItem[] apps;
    private LayoutInflater inflater;
    private ListAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if (screenNO < screenCount - 1 && apps.length % COUNT_PER_SCREEN != 0) {
                return apps.length % COUNT_PER_SCREEN;
            }
            return COUNT_PER_SCREEN;
        }

        @Override
        public AppItem getItem(int position) {
            return apps[screenNO * COUNT_PER_SCREEN + position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = inflater.inflate(R.layout.icon_label, null);
            }
            ImageView imageView = view.findViewById(R.id.icon);
            imageView.setImageResource(getItem(position).getIcon());
            TextView textView = view.findViewById(R.id.label);
            textView.setText(getItem(position).getLabel());
            return view;
        }
    };

    private static class AppItem {
        private String label;
        private int icon;

        public AppItem(String label, int icon) {
            this.label = label;
            this.icon = icon;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_switcher);

        apps = new AppItem[appCount];
        apps[0] = new AppItem("李白", R.drawable.libai);
        apps[1] = new AppItem("杜甫", R.drawable.dufu);
        apps[2] = new AppItem("李商隐", R.drawable.lishangyin);
        apps[3] = new AppItem("杜牧", R.drawable.dumu);
        apps[4] = new AppItem("白居易", R.drawable.baijuyi);
        apps[5] = new AppItem("陆游", R.drawable.luyou);
        screenCount = apps.length % COUNT_PER_SCREEN == 0 ? apps.length / COUNT_PER_SCREEN : apps.length / COUNT_PER_SCREEN + 1;

        viewSwitcher = findViewById(R.id.viewSwitch);
        inflater = LayoutInflater.from(this);
        viewSwitcher.setFactory(() -> inflater.inflate(R.layout.slide_view, null));
        next(null);
    }

    public void prev(View view) {
        if (screenNO > 0) {
            --screenNO;
            viewSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
            viewSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
            ((GridView) viewSwitcher.getNextView()).setAdapter(adapter);
            viewSwitcher.showPrevious();
        }
    }

    public void next(View view) {
        if (screenNO < screenCount - 1) {
            ++screenNO;
            viewSwitcher.setInAnimation(this, R.anim.slide_in_right);
            viewSwitcher.setOutAnimation(this, R.anim.slide_out_left);
            ((GridView) viewSwitcher.getNextView()).setAdapter(adapter);
            viewSwitcher.showNext();
        }
    }
}