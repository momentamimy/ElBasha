package com.ElBasha.mob.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ElBasha.mob.app.Controller.adapter_fav;

import java.util.ArrayList;

public class favourite_list extends AppCompatActivity {

    ImageView back;
    RecyclerView fav;
    private GridLayoutManager mGridLayoutManager;
    adapter_fav adapter;
    private ArrayList<String> testData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       fav=findViewById(R.id.recycleview_fav);
       mGridLayoutManager = new GridLayoutManager(this, 2);

        fav.setLayoutManager(mGridLayoutManager);
        adapter=new adapter_fav(testData,this);
        fav.setAdapter(adapter);
        int spanCount = 3; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        fav.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        fav.setItemAnimator(new DefaultItemAnimator());





    }
}
