package com.ElBasha.mob.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Controller.adapter_fav;
import com.ElBasha.mob.app.Model.Database_Helper;
import com.ElBasha.mob.app.Model.Tables.fav;
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.Retrofit.favJSON;
import com.ElBasha.mob.app.Retrofit.retrofitHead;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class favourite_list extends AppCompatActivity {

    ImageView back;
    RecyclerView favrecycle;
    private GridLayoutManager mGridLayoutManager;
    adapter_fav adapter;
    private ArrayList<String> testData;
    RelativeLayout parent;
    Database_Helper db;
    TextView emptytext;
    ImageView emptyimg,nointernet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        db=new Database_Helper(this);



        List<fav> arrayList=new ArrayList<>();
        ArrayList<String> list=new ArrayList<>();

        emptytext=findViewById(R.id.favemptytext);
        emptyimg=findViewById(R.id.imageemptyfav);
        parent=findViewById(R.id.parentlayfav);
        nointernet=findViewById(R.id.imageNoInternetfav);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favrecycle=findViewById(R.id.recycleview_fav);
        mGridLayoutManager = new GridLayoutManager(this, 2);


        //Get data from database
        arrayList=db.getAllFavProductlist();

        //Check if Empty
        if (arrayList == null||arrayList.size()<=0){
            emptytext.setVisibility(View.VISIBLE);
            emptyimg.setVisibility(View.VISIBLE);
            return;
        }else {
            emptytext.setVisibility(View.GONE);
            emptyimg.setVisibility(View.GONE);
        }

        //Add all
        for(int i = 0; i< arrayList.size(); i++){
            list.add(String.valueOf(arrayList.get(i).getId_api_fav()));
        }

        favJSON favJSON=new favJSON();
        favJSON.setId(list);

        //request Get Request
        requestGetFavProduct(favJSON);


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    //like request
    void requestGetFavProduct(favJSON favlist){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                nointernet.setVisibility(View.GONE);

                final ProgressDialog mProgressDialog = new ProgressDialog(favourite_list.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getFavProduct(favlist);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>>response) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {

                            Log.d("dahLogGETFAV", String.valueOf(response.body()));

                            DisplayResultRecycle(response.body());



                        }else {
                            Snackbar snackbar = Snackbar.make(parent, R.string.Failure_Please_try_again, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(parent, R.string.Failure_Please_try_again, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });


            }else {
                Snackbar snackbar = Snackbar.make(parent, R.string.Internet_not_access_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
                snackbar.show();

                nointernet.setVisibility(View.VISIBLE);

            }

        }else {
            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();

            nointernet.setVisibility(View.VISIBLE);

        }
    }

    private void DisplayResultRecycle(List<ProductModel> list) {
        favrecycle.setLayoutManager(mGridLayoutManager);
        adapter=new adapter_fav(list,this);
        favrecycle.setAdapter(adapter);
        int spanCount = 2; // 2 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        favrecycle.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        favrecycle.setItemAnimator(new DefaultItemAnimator());

    }

}
