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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Controller.RecyclePriceAdapter;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RelativeLayout showDialog;
    PopupWindow popupWindow;
    List<IconPowerMenuItem> list=new ArrayList<>();
    RelativeLayout spinnertext;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        list.add(new IconPowerMenuItem(getResources().getString(R.string.less_than_1000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_1000_2000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_2000_2500)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_2500_3000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_3000_3500)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_3500_4000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_4000_5000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_5000_6000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.range_6000_7000)));
        list.add(new IconPowerMenuItem(getResources().getString(R.string.more_than_7000)));

        backarrow=findViewById(R.id.back);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                showPopup(showDialog);

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
        popupWindow.showAsDropDown(v,Gravity.NO_GRAVITY, 60);

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
