package com.ElBasha.mob.app.Controller;

import android.app.Activity;
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
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.recycleviewHolder;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

/**
 * Created by khaled-pc on 6/9/2019.
 */

public class adapter_fav  extends RecyclerView.Adapter<Holder_fav> {


    private List<ProductModel> data;
    private Context context;

    public adapter_fav(List<ProductModel> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @Override
    public Holder_fav onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_recycle, null); //azkar_recycleview
        return new Holder_fav(layoutView);
    }


    @Override
    public void onBindViewHolder(final Holder_fav holder, final int position) {

        // holder.text.setText(data.get(position));

        if (!data.get(position).getImg().isEmpty())
        {

            holder.rotateLoading.start();
            Picasso.with(context).load(data.get(position).getImg())
                    .error(R.drawable.ic_smartphone_empty).fit().centerCrop().into(holder.mobile_pic,new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.rotateLoading.stop();
                }

                @Override
                public void onError() {
                    holder.rotateLoading.stop();
                }
            });
        }
        else
        {
            holder.mobile_pic.setImageResource(R.drawable.ic_smartphone_empty);
        }
       //Picasso.with(context).load(data.get(position).getImg()).fit().centerCrop().into(holder.mobile_pic);

        if(!data.get(position).getName().isEmpty()){
            holder.text.setText(data.get(position).getName());
        }


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CardSwipeSpecesActivity.class);
                i.putExtra("productModel",data.get(position));
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }


   }