package com.venkat.exampletask;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sheet1 implements Parcelable {

    @SerializedName("EPICNo")
    @Expose
    private String ePICNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SpouseOrFatherName")
    @Expose
    private String spouseOrFatherName;
    @SerializedName("HouseNo")
    @Expose
    private String houseNo;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Division")
    @Expose
    private String division;
    @SerializedName("Booth")
    @Expose
    private String booth;
    @SerializedName("Referred")
    @Expose
    private String referred;
    @SerializedName("Referee")
    @Expose
    private String referee;

    protected Sheet1(Parcel in) {
        ePICNo = in.readString();
        name = in.readString();
        spouseOrFatherName = in.readString();
        houseNo = in.readString();
        age = in.readString();
        gender = in.readString();
        division = in.readString();
        booth = in.readString();
        referred = in.readString();
        referee = in.readString();
    }

    public static final Creator<Sheet1> CREATOR = new Creator<Sheet1>() {
        @Override
        public Sheet1 createFromParcel(Parcel in) {
            return new Sheet1(in);
        }

        @Override
        public Sheet1[] newArray(int size) {
            return new Sheet1[size];
        }
    };

    public String getePICNo() {
        return ePICNo;
    }

    public void setePICNo(String ePICNo) {
        this.ePICNo = ePICNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpouseOrFatherName() {
        return spouseOrFatherName;
    }

    public void setSpouseOrFatherName(String spouseOrFatherName) {
        this.spouseOrFatherName = spouseOrFatherName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBooth() {
        return booth;
    }

    public void setBooth(String booth) {
        this.booth = booth;
    }

    public String getReferred() {
        return referred;
    }

    public void setReferred(String referred) {
        this.referred = referred;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ePICNo);
        parcel.writeString(name);
        parcel.writeString(spouseOrFatherName);
        parcel.writeString(houseNo);
        parcel.writeString(age);
        parcel.writeString(gender);
        parcel.writeString(division);
        parcel.writeString(booth);
        parcel.writeString(referred);
        parcel.writeString(referee);
    }
}