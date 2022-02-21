package com.example.sample;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable {

    private String province;
    private String city;

    public SimpleData(){

    }

    public String  getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String  getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SimpleData{" +
                "province=" + province +
                ", city=" + city +
                '}';
    }

    protected SimpleData(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<SimpleData> CREATOR = new Creator<SimpleData>() {
        @Override
        public SimpleData createFromParcel(Parcel in) {
            return new SimpleData(in);
        }

        @Override
        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(province);
        dest.writeString(city);
    }

    public  void readFromParcel(Parcel in) {
        province = in.readString();
        city = in.readString();
    }
}
