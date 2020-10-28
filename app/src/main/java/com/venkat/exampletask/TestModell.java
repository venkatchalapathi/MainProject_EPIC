package com.venkat.exampletask;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestModell implements Parcelable {
    @SerializedName("Sheet1")
    @Expose
    private List<Sheet1> sheet1 = null;

    protected TestModell(Parcel in) {
    }

    public static final Creator<TestModell> CREATOR = new Creator<TestModell>() {
        @Override
        public TestModell createFromParcel(Parcel in) {
            return new TestModell(in);
        }

        @Override
        public TestModell[] newArray(int size) {
            return new TestModell[size];
        }
    };

    public List<Sheet1> getSheet1() {
        return sheet1;
    }

    public void setSheet1(List<Sheet1> sheet1) {
        this.sheet1 = sheet1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
