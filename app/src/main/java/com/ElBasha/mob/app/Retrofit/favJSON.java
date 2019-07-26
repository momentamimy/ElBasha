package com.ElBasha.mob.app.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by khaled-pc on 7/25/2019.
 */

public class favJSON {

    @SerializedName("ids")
    @Expose
    private ArrayList<String> id;

    public ArrayList<String> getId() {
        return id;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }
}
