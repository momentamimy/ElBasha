package com.ElBasha.mob.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by khaled-pc on 6/9/2019.
 */

public class Holder_fav extends RecyclerView.ViewHolder {


   public TextView text;
   public ImageView mobile_pic;
   public CardView parent;


    public Holder_fav(View itemView) {
        super(itemView);


        text = itemView.findViewById(R.id.text);
        mobile_pic=itemView.findViewById(R.id.mobileimage);
        parent=itemView.findViewById(R.id.parent);

    }
}
