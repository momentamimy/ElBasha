package com.ElBasha.mob.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by khaled-pc on 5/16/2019.
 */

public class recycleviewHolder extends RecyclerView.ViewHolder {


  public   TextView text;


    public recycleviewHolder(View itemView) {
        super(itemView);


        text = itemView.findViewById(R.id.price_text);


    }
}