package com.example.sample.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sample.R;

import androidx.appcompat.app.AppCompatActivity;

public class ExpandableListViewActivity extends AppCompatActivity {

    private final String[] armTypes = new String[]{"神族兵种", "虫族兵种", "人族兵种"};
    private final String[][] arms = new String[][]{{"狂战士", "龙骑士", "黑暗圣堂", "电兵"}, {"小狗", "刺蛇", "飞龙", "自爆飞机"}, {"机枪兵", "护士MM", "幽灵"}};
    private final int[] logos = new int[]{R.drawable.libai, R.drawable.dufu, R.drawable.baijuyi};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return armTypes.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return arms[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return arms[groupPosition];
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return arms[groupPosition][childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                LinearLayout linearLayout = new LinearLayout(ExpandableListViewActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                ImageView imageView = new ImageView(ExpandableListViewActivity.this);
                imageView.setImageResource(logos[groupPosition]);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                linearLayout.addView(imageView);
                TextView textView = new TextView(ExpandableListViewActivity.this);
                textView.setText(armTypes[groupPosition]);
                textView.setTextSize(26);
                linearLayout.addView(textView);
                return linearLayout;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView =new TextView(ExpandableListViewActivity.this);
                textView.setText(arms[groupPosition][childPosition]);
                AbsListView.LayoutParams params =new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
                textView.setLayoutParams(params);
                textView.setPadding(36, 0, 0, 0);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                return textView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        };

        ExpandableListView expandableListView = findViewById(R.id.expanded);
        expandableListView.setAdapter(adapter);
    }
}