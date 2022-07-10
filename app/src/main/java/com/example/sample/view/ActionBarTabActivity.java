// package com.example.sample.view;
//
// import android.os.Bundle;
// import android.view.Gravity;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.ArrayAdapter;
// import android.widget.TextView;
//
// import com.example.sample.R;
//
// import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;
// import androidx.appcompat.app.ActionBar;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.fragment.app.Fragment;
// import androidx.fragment.app.FragmentTransaction;
//
// public class ActionBarTabActivity extends AppCompatActivity implements ActionBar.TabListener, ActionBar.OnNavigationListener {
//
//     private static final String SELECTED_ITEM = "selected_item";
//
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_action_bar_tab);
//
//         ActionBar actionBar = getSupportActionBar();
//
//         // 1.NAVIGATION_MODE_TABS
//         // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//         // actionBar.addTab(actionBar.newTab().setText("第一页").setTabListener(this));
//         // actionBar.addTab(actionBar.newTab().setText("第二页").setTabListener(this));
//         // actionBar.addTab(actionBar.newTab().setText("第三页").setTabListener(this));
//         // 2.NAVIGATION_MODE_LIST
//         actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//         actionBar.setListNavigationCallbacks(new ArrayAdapter<>(this,  R.layout.array_item, R.id.tv, new String[]{"第一页", "第二页", "第三页"}), this);
//
//         // has no effect
//         // actionBar.setDisplayShowCustomEnabled(true);
//         // actionBar.setDisplayShowHomeEnabled(false);
//     }
//
//     @Override
//     public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//         Fragment fragment = new SimpleFragment();
//         Bundle bundle = new Bundle();
//         bundle.putInt("number", tab.getPosition() + 1);
//         fragment.setArguments(bundle);
//         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//         transaction.replace(R.id.container, fragment);
//         transaction.commit();
//     }
//
//     @Override
//     public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//     }
//
//     @Override
//     public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//     }
//
//     @Override
//     protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//         if (savedInstanceState.containsKey(SELECTED_ITEM)) {
//             getSupportActionBar().setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_ITEM));
//         }
//         super.onRestoreInstanceState(savedInstanceState);
//     }
//
//     @Override
//     protected void onSaveInstanceState(@NonNull Bundle outState) {
//         outState.putInt(SELECTED_ITEM, getSupportActionBar().getSelectedNavigationIndex());
//         super.onSaveInstanceState(outState);
//     }
//
//     @Override
//     public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//         Fragment fragment = new SimpleFragment();
//         Bundle bundle = new Bundle();
//         bundle.putInt("number", itemPosition + 1);
//         fragment.setArguments(bundle);
//         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//         transaction.replace(R.id.container, fragment);
//         transaction.commit();
//         return false;
//     }
//
//     public static class SimpleFragment extends Fragment {
//
//         @Nullable
//         @Override
//         public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//             TextView textView  = new TextView(getActivity());
//             textView.setGravity(Gravity.START);
//             textView.setTextSize(30);
//             textView.setText("" +getArguments().getInt("number"));
//             return textView;
//         }
//     }
// }