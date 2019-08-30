package com.ElBasha.mob.app.Retrofit;

import com.google.gson.annotations.SerializedName;

public class IntroResponse {
    @SerializedName("0")
    private String num0;

    @SerializedName("1")
    private String num1;

    @SerializedName("2")
    private String num2;


    public IntroResponse(String num0, String num1, String num2) {
        this.num0 = num0;
        this.num1 = num1;
        this.num2 = num2;
    }

    public String getNum0() {
        return num0;
    }

    public void setNum0(String num0) {
        this.num0 = num0;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }
}
