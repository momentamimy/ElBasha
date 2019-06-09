package com.ElBasha.mob.app.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ElBasha.mob.app.CardSwipeSpecesActivity;
import com.ElBasha.mob.app.Holder_fav;
import com.ElBasha.mob.app.R;
import com.ElBasha.mob.app.recycleviewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by khaled-pc on 6/9/2019.
 */

public class adapter_fav  extends RecyclerView.Adapter<Holder_fav> {


    private List<String> data;
    private Context context;

    public adapter_fav(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @Override
    public Holder_fav onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardiremmobile, null); //azkar_recycleview
        return new Holder_fav(layoutView);
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder_fav holder, final int position) {

     holder.text.setText(data.get(position));
     Picasso.with(context).load(R.drawable.dummy3).fit().centerCrop().into(holder.mobile_pic);

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }


   }