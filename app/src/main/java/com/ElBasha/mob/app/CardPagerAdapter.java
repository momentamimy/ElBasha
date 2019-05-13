package com.ElBasha.mob.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CardPagerAdapter extends BaseAdapter {

    ArrayList<Integer> Images;
    Context context;
    public CardPagerAdapter(Context context, ArrayList<Integer> Images)
    {
        this.context=context;
        this.Images=Images;
    }

    @Override
    public int getCount() {
        return Images.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.card_pager_layout,parent,true);
        }
        return convertView;
    }
}
