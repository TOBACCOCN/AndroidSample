package com.example.sample.databinding;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

public class Food extends BaseObservable {

    private String image;

    private String desc;

    private String keywords;

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void onItemClick(View view) {
        Toast.makeText(view.getContext(), getDesc(), Toast.LENGTH_LONG).show();
        setDesc("clicked clicked clicked clicked clicked clicked clicked clicked ");
    }

    public Food(String image, String desc, String keywords) {
        this.image = image;
        this.desc = desc;
        this.keywords = keywords;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
