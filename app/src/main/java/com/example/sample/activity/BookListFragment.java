package com.example.sample.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class BookListFragment extends ListFragment {

    private Callback mCallback;

    interface Callback {
        void onListItemSelect(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.array_item, R.id.tv, BookManager.books);
        setListAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Callback)) {
            try {
                String msg = context.getPackageName() + " must implement BookListFragment.Callback";
                throw new IllegalAccessException(msg);
            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(e);
            }
        } else {
            mCallback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onListItemSelect(position);
    }

    void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }
}