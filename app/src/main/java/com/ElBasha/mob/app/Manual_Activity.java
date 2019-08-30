package com.ElBasha.mob.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.Retrofit.RamBodyModel;
import com.ElBasha.mob.app.Retrofit.RangeBodyModel;
import com.ElBasha.mob.app.Retrofit.retrofitHead;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Manual_Activity extends AppCompatActivity {

    Spinner spinner,spinnerROM,spinnerCPU,spinnerBattery,spinnerScreen,spinnerSoftware;
    ImageView fav_list,back;
    Button searchbtn;
    RelativeLayout parent;
    ArrayList<String> RamArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_);

        parent=findViewById(R.id.parentM);
        fav_list=findViewById(R.id.favoratelist);
        searchbtn=findViewById(R.id.searchbtn);
        spinner=(Spinner) findViewById(R.id.spinner);  // connect 1st spinner
        spinnerROM=(Spinner) findViewById(R.id.spinnerRom);  // connect 2end spinner
        spinnerCPU=(Spinner) findViewById(R.id.spinnerCPU);  // connect 3 spinner
        spinnerBattery=(Spinner) findViewById(R.id.spinnerBattery);  // connect 4 spinner
        spinnerScreen=(Spinner) findViewById(R.id.spinnerScreen);  // connect 5 spinner
        spinnerSoftware=(Spinner) findViewById(R.id.spinnerOS);  // connect 6 spinner

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getAllSpinnerArrayRequest();

        //search btn and get result
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
                    //Check internet Access
                    if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                      if(spinner.getSelectedItem().toString()!="RAM"){

                          //&&spinnerROM.getSelectedItem().toString()!="ROM/Storage"&&spinnerCPU.getSelectedItem().toString()!="CPU"&&spinnerBattery.getSelectedItem().toString()!="Battery"

                          Intent intent = new Intent(Manual_Activity.this, swipcards.class);
                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          intent.putExtra("TypeActivity","Manual_Activity");
                          intent.putExtra("RAM",spinner.getSelectedItem().toString());
                          intent.putExtra("ROM",spinnerROM.getSelectedItem().toString());
                          intent.putExtra("BATTERY",spinnerBattery.getSelectedItem().toString());
                          intent.putExtra("PROCESSOR",spinnerCPU.getSelectedItem().toString());
                          intent.putExtra("OS",spinnerSoftware.getSelectedItem().toString());
                          intent.putExtra("SCREEN",spinnerScreen.getSelectedItem().toString());
                          startActivity(intent);
                          finish();
                      }else
                          {
                            Snackbar snackbar = Snackbar.make(parent, "Please,Select one from list", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }


                    }else {
                            Snackbar snackbar = Snackbar.make(parent, R.string.Internet_not_access_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                }else {
                    Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        //open favourite list activity
        fav_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), favourite_list.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });








    }

    //Request To OTher Spinner
    private void requestToOtherSpinner() {
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                RangeBodyModel rangeBodyModel = new RangeBodyModel(20,55);
                Retrofit retrofit = retrofitHead.headOfGetorPostReturnRes();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataPriceRange = elBashaApi.getDataPriceRange(rangeBodyModel);

                dataPriceRange.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();


                        if (response.isSuccessful())
                        {
                            Log.d("dahLog",response.body().get(0).getName());
                            //Set Other Spinner Info



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

            }
        }else {
            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    void getAllSpinnerArrayRequest(){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByValueRAM();

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {


                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError())) {

                                getRamSpinnerArray(response.body());
                                getRomSpinnerArray(response.body());
                                getCPUSpinnerArray(response.body());
                                getBatterySpinnerArray(response.body());
                                getScreenSpinnerArray(response.body());


                            }else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


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

            }

        }else {

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    /*
    void getRamSpinnerArrayRequest(){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByValueRAM();

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {


                             Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))

                                getRamSpinnerArray(response.body());

                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


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

            }

        }else {

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }*/


    private void getRamSpinnerArray(List<ProductModel> body) {

        ArrayList<String> array = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            if(body.get(i).getRam()!=null&&!body.get(i).getRam().equals("")){
                array.add(String.valueOf(body.get(i).getRam()));
            }
        }


        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(array);
        array.clear();
        array.add("RAM");
        array.addAll(hashSet);


        for(int i = 0; i< array.size(); i++){
            Log.w("aa",array.get(i));
        }


        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(this, R.layout.spinner_item_text,array);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              /*  if(position!=0){

                    //Show Other Spinner
                    if (spinnerROM.getVisibility() == View.GONE) {
                        // Its visible
                        spinnerROM.setVisibility(View.VISIBLE);
                    }


                    //Set visible Gone
                    if (spinnerCPU.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerCPU.setVisibility(View.GONE);
                    }
                    if (spinnerBattery.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerBattery.setVisibility(View.GONE);
                    }
                    if (spinnerScreen.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerScreen.setVisibility(View.GONE);
                    }
                    if (spinnerSoftware.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerSoftware.setVisibility(View.GONE);
                    }

                    // requestToOtherSpinner();
                    getROMSpinnerArrayRequest(spinner.getSelectedItem().toString());



                }else if(position==0) {
                    spinnerROM.setVisibility(View.GONE);
                    spinnerCPU.setVisibility(View.GONE);
                    spinnerBattery.setVisibility(View.GONE);
                    spinnerScreen.setVisibility(View.GONE);
                    spinnerSoftware.setVisibility(View.GONE);
                }
                     */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }
        });


    }

    /*
    void getROMSpinnerArrayRequest(String Ram){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByROM("application/x-www-form-urlencoded",Ram);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {


                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))

                                getRomSpinnerArray(response.body());

                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


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

            }

        }else {

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }*/

    private void getRomSpinnerArray(List<ProductModel> body) {

        ArrayList<String> array = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            if(body.get(i).getStorage()!=null&&!body.get(i).getStorage().equals("")){
                array.add(String.valueOf(body.get(i).getStorage()));
            }
        }


        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(array);
        array.clear();
        array.add("ROM/Storage");
        array.addAll(hashSet);


        for(int i = 0; i< array.size(); i++){
            Log.w("aa",array.get(i));
        }


        //******************************************************ArrayAdapter for ROM spinner**********************************************************************
        ArrayAdapter<String> myAdapter2= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,array);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerROM.setAdapter(myAdapter2);
        spinnerROM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

                /*if(position!=0){

                    if (spinnerCPU.getVisibility() == View.GONE) {
                        // Its visible
                        spinnerCPU.setVisibility(View.VISIBLE);
                    }

                    //set visibality Gone
                    if (spinnerBattery.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerBattery.setVisibility(View.GONE);
                    }
                    if (spinnerScreen.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerScreen.setVisibility(View.GONE);
                    }
                    if (spinnerSoftware.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerSoftware.setVisibility(View.GONE);
                    }

                    // requestToOtherSpinner();
                    getCPUSpinnerArrayRequest(spinner.getSelectedItem().toString(),spinnerROM.getSelectedItem().toString());


                }else if(position==0) {
                    spinnerCPU.setVisibility(View.GONE);
                    spinnerBattery.setVisibility(View.GONE);
                    spinnerScreen.setVisibility(View.GONE);
                    spinnerSoftware.setVisibility(View.GONE);
                }*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerROM.setSelection(0);
            }
        });

    }


/*
    void getCPUSpinnerArrayRequest(String Ram,String ROM){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByCPUOther("application/x-www-form-urlencoded",Ram,ROM);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {


                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))

                                getCPUSpinnerArray(response.body());

                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


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

            }

        }else {

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }*/


    private void getCPUSpinnerArray(List<ProductModel> body) {

        ArrayList<String> array = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            if(body.get(i).getProcessor()!=null&&!body.get(i).getProcessor().equals("")){
                array.add(String.valueOf(body.get(i).getProcessor()));
            }
        }


        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(array);
        array.clear();
        array.add("CPU");
        array.addAll(hashSet);


        for(int i = 0; i< array.size(); i++){
            Log.w("aa",array.get(i));
        }



        //******************************************************ArrayAdapter for CPU spinner**********************************************************************
        ArrayAdapter<String> myAdapter3= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,array);
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCPU.setAdapter(myAdapter3);
        spinnerCPU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             /*   if(position!=0){


                    if (spinnerBattery.getVisibility() == View.GONE) {
                        // Its visible
                        spinnerBattery.setVisibility(View.VISIBLE);
                    }


                    //set visibility Gone
                    if (spinnerScreen.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerScreen.setVisibility(View.GONE);
                    }
                    if (spinnerSoftware.getVisibility() == View.VISIBLE) {
                        // Its visible
                        spinnerSoftware.setVisibility(View.GONE);
                    }

                    // requestToOtherSpinner();
                    getBatterySpinnerArrayRequest(spinner.getSelectedItem().toString(),spinnerROM.getSelectedItem().toString(),spinnerCPU.getSelectedItem().toString());


                }else if(position==0) {
                    spinnerBattery.setVisibility(View.GONE);
                    spinnerScreen.setVisibility(View.GONE);
                    spinnerSoftware.setVisibility(View.GONE);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerCPU.setSelection(0);
            }
        });


    }


/*
    void getBatterySpinnerArrayRequest(String Ram,String ROM,String cpu){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataBybattery("application/x-www-form-urlencoded",Ram,ROM,cpu);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {


                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))

                                getBatterySpinnerArray(response.body());

                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


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

            }

        }else {

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    } */


    //Battery Spinner
    private void getBatterySpinnerArray(List<ProductModel> body) {

        ArrayList<String> array = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            if(body.get(i).getBattery()!=null&&!body.get(i).getBattery().equals("")){
                array.add(String.valueOf(body.get(i).getBattery()));
            }
        }


        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(array);
        array.clear();
        array.add("Battery");
        array.addAll(hashSet);


        for(int i = 0; i< array.size(); i++){
            Log.w("aa",array.get(i));
        }


        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter4= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,array);
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBattery.setAdapter(myAdapter4);
        spinnerBattery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);
               /* if(position!=0){


                    if (spinnerScreen.getVisibility() == View.GONE) {
                        // Its visible
                        spinnerScreen.setVisibility(View.VISIBLE);
                    }


                    if (spinnerSoftware.getVisibility() == View.GONE) {
                        // Its visible
                        spinnerSoftware.setVisibility(View.VISIBLE);
                    }

                    // requestToOtherSpinner();
                    //getScreenSpinnerArrayRequest(spinner.getSelectedItem().toString(),spinnerROM.getSelectedItem().toString(),spinnerCPU.getSelectedItem().toString(),spinnerBattery.getSelectedItem().toString());


                }else if(position==0) {
                   spinnerScreen.setVisibility(View.GONE);
                   spinnerSoftware.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerBattery.setSelection(0);
            }
        });

    }

/*
    void getScreenSpinnerArrayRequest(String Ram,String ROM,String cpu,String Battery){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(Manual_Activity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByScreen("application/x-www-form-urlencoded",Ram,ROM,cpu,Battery);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {


                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))

                                getScreenSpinnerArray(response.body());

                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


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

            }

        }else {

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }
*/

    //Battery Spinner
    private void getScreenSpinnerArray(List<ProductModel> body) {

        //Screen Spinner
        ArrayList<String> array = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            if(body.get(i).getScreen()!=null&&!body.get(i).getScreen().equals("")){
                array.add(String.valueOf(body.get(i).getScreen()));
            }
        }

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(array);
        array.clear();
        array.add("Screen");
        array.addAll(hashSet);


        //OS Spinner
        ArrayList<String> arrayos = new ArrayList<>();

        for(int i = 0; i< body.size(); i++){
            if(body.get(i).getOs()!=null&&!body.get(i).getOs().equals("")){
                arrayos.add(String.valueOf(body.get(i).getOs()));
            }
        }

        HashSet<String> hashSet2 = new HashSet<String>();
        hashSet2.addAll(arrayos);
        arrayos.clear();
        arrayos.add("Software/OS");
        arrayos.addAll(hashSet2);



        //******************************************************ArrayAdapter for SCreen spinner**********************************************************************
        ArrayAdapter<String> myAdapter6= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,array);
        myAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerScreen.setAdapter(myAdapter6);
        spinnerScreen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* if(position==0) {
                    //spinnerScreen.setVisibility(View.GONE);
                    //spinnerSoftware.setVisibility(View.GONE);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerScreen.setSelection(0);
            }
        });


        //******************************************************ArrayAdapter for OS spinner**********************************************************************
        ArrayAdapter<String> myAdapter5= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,arrayos);
        myAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSoftware.setAdapter(myAdapter5);
        spinnerSoftware.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              /*  if(position==0) {

                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerSoftware.setSelection(0);
            }
        });



    }


}
