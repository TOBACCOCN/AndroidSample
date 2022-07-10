package com.example.sample.databinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class MyBaseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Food> foods;
    private int resId;
    private int variableId;

    public MyBaseAdapter(Context context, List<Food> foods, int resId, int variableId) {
        inflater = LayoutInflater.from(context);
        this.foods = foods;
        this.resId = resId;
        this.variableId = variableId;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding dataBinding;
        if (convertView == null) {
            dataBinding = DataBindingUtil.inflate(inflater, resId, parent, false);
        } else {
            dataBinding = DataBindingUtil.findBinding(convertView);
        }
        dataBinding.setVariable(variableId, foods.get(position));
        return dataBinding.getRoot();
    }
}
