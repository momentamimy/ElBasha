package com.ElBasha.mob.app.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ElBasha.mob.app.IconPowerMenuItem;
import com.ElBasha.mob.app.R;
import com.ElBasha.mob.app.recycleviewHolder;

import java.util.List;

/**
 * Created by khaled-pc on 5/16/2019.
 */

public class RecyclePriceAdapter extends RecyclerView.Adapter<recycleviewHolder> {

    private List<IconPowerMenuItem> itemList;
    private Context context;


    public RecyclePriceAdapter(Context context, List<IconPowerMenuItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public recycleviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        return new recycleviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  recycleviewHolder holder,  int position) {

        holder.text.setText(itemList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


}
