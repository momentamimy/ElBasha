package com.ElBasha.mob.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.MaxBodyModel;
import com.ElBasha.mob.app.Retrofit.MinBodyModel;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.Retrofit.RamBodyModel;
import com.ElBasha.mob.app.Retrofit.RangeBodyModel;
import com.ElBasha.mob.app.Retrofit.retrofitHead;
import com.daprlabs.cardstack.SwipeDeck;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class swipcards extends AppCompatActivity {

    private static final String TAG = "swipcards";
    private SwipeDeck cardStack;
    private Context context = this;

    private SwipeDeckAdapter adapter;
    private ArrayList<String> testData;
    private List<ProductModel> RealData;
    ImageView backarrow,close,fav_list;
    RelativeLayout parent;

    String Type_Activity;

    String o_software,processor;
    int Ram,Rom,battery,screen;

    String priceRange;
    String charName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipcards);



        parent=findViewById(R.id.parentcards);
        fav_list=findViewById(R.id.openfavList);
        backarrow=findViewById(R.id.back);
        close=findViewById(R.id.close);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);

        //open favourite list activity
        fav_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), favourite_list.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });


        getAllIntent();


        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("swipcards", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("swipcards", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("swipcards", "no more cards");
            }

            @Override
            public void cardActionDown() {
                Log.i(TAG, "cardActionDown");
            }

            @Override
            public void cardActionUp() {
                Log.i(TAG, "cardActionUp");
            }

        });

        ImageView btn3 = (ImageView) findViewById(R.id.reload);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  testData.add("a sample string.");
//                ArrayList<String> newData = new ArrayList<>();
//                newData.add("some new data");
//                newData.add("some new data");
//                newData.add("some new data");
//                newData.add("some new data");
//
//                SwipeDeckAdapter adapter = new SwipeDeckAdapter(newData, context);
//                cardStack.setAdapter(adapter);
                  adapter.notifyDataSetChanged();
            }
        });


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManualOrCharacterActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManualOrCharacterActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        callPriceRequest();
    }

    private void getAllIntent() {

        Intent intent = getIntent();
        Type_Activity = intent.getStringExtra("TypeActivity");

        if(Type_Activity.equals("Manual_Activity")){

            Ram= Integer.parseInt(intent.getStringExtra("RAM"));
            Rom= Integer.parseInt(intent.getStringExtra("ROM"));
            o_software=intent.getStringExtra("OS");
            battery= Integer.parseInt(intent.getStringExtra("BATTERY"));
            processor=intent.getStringExtra("PROCESSOR");
            screen= Integer.parseInt(intent.getStringExtra("SCREEN"));

            //send Request
            requestManualSearch(Ram,Rom,battery,screen,processor,o_software);

        }else if(Type_Activity.equals("MainActivity")){

            priceRange = intent.getStringExtra("priceRange");
            charName=intent.getStringExtra("charName");


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ManualOrCharacterActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    public class SwipeDeckAdapter extends BaseAdapter {

        private List<ProductModel> data;
        private Context context;

        public SwipeDeckAdapter(List<ProductModel> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.cardiremmobile, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            ImageView imageView = (ImageView) v.findViewById(R.id.mobileimage);
            if (!data.get(position).getImg().isEmpty())
                Picasso.with(context).load(data.get(position).getImg())
                        .error(R.drawable.ic_sad).fit().centerCrop().into(imageView ,new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            //String item = (String)getItem(position);
            TextView textView = (TextView) v.findViewById(R.id.mobilename);
            //String item = (String)getItem(position);
            textView.setText(data.get(position).getName());

            Toast.makeText(getApplicationContext(),"mashyya3m",Toast.LENGTH_LONG).show();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hwardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    Intent i = new Intent(v.getContext(), CardSwipeSpecesActivity.class);
                    v.getContext().startActivity(i);


                }
            });
            return v;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    void requestManualSearch(int ram,int storage,int battery,int screen,String processor,String os){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(swipcards.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                RamBodyModel ramBodyModel = new RamBodyModel(ram,storage,battery,screen,processor,os);
                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByValue2("application/x-www-form-urlencoded",ram,storage,battery,screen,processor,os);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {
                            Log.d("dahLog",response.body().get(0).getName());
                            displayResultRecycleview(response.body());


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

    private void displayResultRecycleview(List<ProductModel> listMobile) {

        testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");

        adapter = new SwipeDeckAdapter(listMobile, this);
        cardStack.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }





/*----------------------------------------------------------------------------------------------------------------------------------------------*/
                                                        /*MainActivity Requests*/
/*----------------------------------------------------------------------------------------------------------------------------------------------*/

    public void priceRangeRequest(int min,int max)
    {
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(swipcards.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                RangeBodyModel rangeBodyModel = new RangeBodyModel(min,max);
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
                            Log.w("dahLog", String.valueOf(response.body().size()));
                            displayResultRecycleview(response.body());

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


    public void priceMinRequest(int min)
    {
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(swipcards.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                MinBodyModel minBodyModel = new MinBodyModel(min);
                Retrofit retrofit = retrofitHead.headOfGetorPostReturnRes();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataPriceMin = elBashaApi.getDataMinPrice(minBodyModel);

                dataPriceMin.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {
                            Log.d("dahLog",response.body().get(0).getName());
                            displayResultRecycleview(response.body());
                        }
                        else
                        {
                            Log.w("dahLog2", String.valueOf(response.errorBody()));
                            Snackbar snackbar = Snackbar.make(parent, R.string.Failure_Please_try_again, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        Log.w("dahLog", String.valueOf(t.getMessage()));
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


    public void priceMaxRequest(int max)
    {
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(swipcards.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                MaxBodyModel maxBodyModel = new MaxBodyModel(max);
                Retrofit retrofit = retrofitHead.headOfGetorPostReturnRes();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataPriceMax = elBashaApi.getDataMaxPrice(maxBodyModel);

                dataPriceMax.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {
                            Log.w("dahLog", String.valueOf(response.body().size()));
                            displayResultRecycleview(response.body());

                        }
                        else
                        {
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



    public void callPriceRequest()
    {
        if (priceRange.equals(getResources().getString(R.string.less_than_1000)))
        {
            priceMaxRequest(1000);
        }


        else if (priceRange.equals(getResources().getString(R.string.range_1000_2000))) {
            priceRangeRequest(1000,2000);
        }else if (priceRange.equals(getResources().getString(R.string.range_2000_2500))){
            priceRangeRequest(2000,2500);
        }else if (priceRange.equals(getResources().getString(R.string.range_2500_3000))){
            priceRangeRequest(2500,3000);
        }else if (priceRange.equals(getResources().getString(R.string.range_3000_3500))){
            priceRangeRequest(3000,3500);
        }else if (priceRange.equals(getResources().getString(R.string.range_3500_4000))){
            priceRangeRequest(3500,4000);
        }else if (priceRange.equals(getResources().getString(R.string.range_4000_5000))){
            priceRangeRequest(4000,5000);
        }else if (priceRange.equals(getResources().getString(R.string.range_5000_6000))){
            priceRangeRequest(5000,6000);
        }else if (priceRange.equals(getResources().getString(R.string.range_6000_7000))){
            priceRangeRequest(6000,7000);
        }


        else if(priceRange.equals(getResources().getString(R.string.more_than_7000)))
        {
            priceMinRequest(7000);
        }
    }
}


