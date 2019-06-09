package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ElBasha.mob.app.Controller.RecyclePriceAdapter;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RelativeLayout showDialog;
    PopupWindow popupWindow;
    List<IconPowerMenuItem> list=new ArrayList<>();
    RelativeLayout spinnertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        list.add(new IconPowerMenuItem("أقل من 1000"));
        list.add(new IconPowerMenuItem("من 1000 الي 2000"));
        list.add(new IconPowerMenuItem("من 2000 الي 2500"));
        list.add(new IconPowerMenuItem("من 2500 الي 3000"));
        list.add(new IconPowerMenuItem("من 3000 الي 3500"));
        list.add(new IconPowerMenuItem("من 3500 الي 4000"));
        list.add(new IconPowerMenuItem("من 4000 الي 5000"));
        list.add(new IconPowerMenuItem("من 5000 الي 6000"));
        list.add(new IconPowerMenuItem("من 6000 الي 7000"));
        list.add(new IconPowerMenuItem("أكثر من 7000"));



        showDialog=findViewById(R.id.showdialog);
        spinnertext=findViewById(R.id.textcard);

        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup(v);


            }
        });


        spinnertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);

            }
        });



    }


    public void showPopup(View v) {




        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_filter_layout, null);

        RecyclerView priceRecycle=(RecyclerView)popupView.findViewById(R.id.priceRecylce);
        priceRecycle.setLayoutManager(new LinearLayoutManager(this));
        priceRecycle.setAdapter(new RecyclePriceAdapter(this, list));


        /*dialogSetting.dismiss();
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        intent.putExtra("typeofzkr","MainActivitySebha");
        startActivity(intent);
        finish();*/

        //Toast.makeText(SearchActivity.this,"aaaAaa",Toast.LENGTH_SHORT);


        priceRecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(this, priceRecycle ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        //Log.w("aa","aaaaaa");
                        //Toast.makeText(SearchActivity.this,"aaaAaa=  "+list.get(position).getTitle(), Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.putExtra("priceRange",list.get(position).getTitle());
                        startActivity(intent);
                        //finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        popupWindow = new PopupWindow(
                popupView,
                v.getMeasuredWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);


        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });

        popupWindow.setAnimationStyle(R.style.Animation);
        popupWindow.showAsDropDown(v,Gravity.NO_GRAVITY, 35);

    }



    @Override
    public void onBackPressed() {
        if(popupWindow!=null){
            if(popupWindow.isShowing()) {
                popupWindow.dismiss();
                return;
            }
        }

        super.onBackPressed();
    }


}
