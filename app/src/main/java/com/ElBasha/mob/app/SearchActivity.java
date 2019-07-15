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
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.Retrofit.RamBodyModel;
import com.ElBasha.mob.app.Retrofit.RangeBodyModel;
import com.ElBasha.mob.app.Retrofit.retrofitHead;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

                        if (list.get(position).getTitle().equals(getResources().getString(R.string.range_1000_2000))) {
                            priceRangeRequest(1000,2000);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_2000_2500))){
                            priceRangeRequest(2000,2500);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_2500_3000))){
                            priceRangeRequest(2500,3000);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_3000_3500))){
                            priceRangeRequest(3000,3500);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_3500_4000))){
                            priceRangeRequest(3500,4000);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_4000_5000))){
                            priceRangeRequest(4000,5000);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_5000_6000))){
                            priceRangeRequest(5000,6000);
                        }else if (list.get(position).getTitle().equals(getResources().getString(R.string.range_6000_7000))){
                            priceRangeRequest(6000,7000);
                        }


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
        popupWindow.showAsDropDown(v,Gravity.NO_GRAVITY, 20);

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



    public void priceRangeRequest(int min,int max)
    {
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                RangeBodyModel rangeBodyModel = new RangeBodyModel(min,max);
                Retrofit retrofit = retrofitHead.headOfGetorPostReturnRes();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataPriceRange = elBashaApi.getDataPriceRange(rangeBodyModel);

                dataPriceRange.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (response.isSuccessful())
                        {
                            Log.d("dahLog",response.body().get(0).getName());

                            popupWindow.dismiss();
                            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                            startActivity(intent);
                            //finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductModel>> call, Throwable t) {

                    }
                });
            }
        }
    }
}
