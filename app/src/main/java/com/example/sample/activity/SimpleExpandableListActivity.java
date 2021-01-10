package com.example.sample.activity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sample.R;

public class SimpleExpandableListActivity extends ExpandableListActivity {

    private final String[] armTypes = new String[]{"神族兵种", "虫族兵种", "人族兵种"};
    private final String[][] arms = new String[][]{{"狂战士", "龙骑士", "黑暗圣堂", "电兵"}, {"小狗", "刺蛇", "飞龙", "自爆飞机"}, {"机枪兵", "护士MM", "幽灵"}};
    private final int[] logos = new int[]{R.drawable.libai, R.drawable.dufu, R.drawable.baijuyi};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpandableListAdapter adapter = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {

            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                return 0;
            }

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
                LinearLayout linearLayout = new LinearLayout(SimpleExpandableListActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                ImageView imageView = new ImageView(SimpleExpandableListActivity.this);
                imageView.setImageResource(logos[groupPosition]);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                linearLayout.addView(imageView);
                TextView textView = new TextView(SimpleExpandableListActivity.this);
                textView.setText(armTypes[groupPosition]);
                textView.setTextSize(26);
                linearLayout.addView(textView);
                return linearLayout;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView = new TextView(SimpleExpandableListActivity.this);
                textView.setText(arms[groupPosition][childPosition]);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
                textView.setLayoutParams(params);
                textView.setPadding(36, 0, 0, 0);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                return textView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };
        setListAdapter(adapter);

        getExpandableListView().setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Intent intent = getIntent();
            intent.putExtra("armType", armTypes[groupPosition]);
            intent.putExtra("arm", arms[groupPosition][childPosition]);
            SimpleExpandableListActivity.this.setResult(0x123, intent);
            SimpleExpandableListActivity.this.finish();
            return false;
        });
        // setContentView(R.layout.activity_simple_expandable_list);
    }
}