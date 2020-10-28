package com.venkat.exampletask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValidateUser {

    @SerializedName("Sheet2")
    @Expose
    private List<Sheet2> sheet2 = null;

    public List<Sheet2> getSheet2() {
        return sheet2;
    }

    public void setSheet2(List<Sheet2> sheet2) {
        this.sheet2 = sheet2;
    }

}

class Sheet2 {

    @SerializedName("Mails")
    @Expose
    private String mails;

    public String getMails() {
        return mails;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }
}
