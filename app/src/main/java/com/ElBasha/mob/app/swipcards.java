package com.ElBasha.mob.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ElBasha.mob.app.Model.Database_Helper;
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.MaxBodyModel;
import com.ElBasha.mob.app.Retrofit.MinBodyModel;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.Retrofit.RamBodyModel;
import com.ElBasha.mob.app.Retrofit.RangeBodyModel;
import com.ElBasha.mob.app.Retrofit.retrofitHead;
import com.daprlabs.cardstack.SwipeDeck;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

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
    Database_Helper db;

    private SwipeDeckAdapter adapter;
    private ArrayList<String> testData;
    private List<ProductModel> RealData;
    ImageView backarrow,close,fav_list,reload,setFav,setLike;
    RelativeLayout parent;
    ImageView imagemobileoops;
    TextView opstext,textnoproduct;


    String Type_Activity;

    String o_software,processor;
    String Ram,Rom,battery,screen;

    String priceRange;
    String charName;
    int pos=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipcards);

        db=new Database_Helper(this);

        imagemobileoops=findViewById(R.id.mobileimageempty);
        opstext=findViewById(R.id.textopps);
        textnoproduct=findViewById(R.id.textnoresultinrecycle);
        parent=findViewById(R.id.parentcards);
        reload=findViewById(R.id.reloadbtn);
        setFav=findViewById(R.id.setfavorate);
        setLike=findViewById(R.id.setlike);
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
                pos++;
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("swipcards", "card was swiped right, position in adapter: " + position);
                pos++;
            }

            @Override
            public void cardsDepleted() {
                Log.i("swipcards", "no more cards");
                pos=-1;
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



        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //testData.add("a sample string.");
//                ArrayList<String> newData = new ArrayList<>();
//                newData.add("some new data");
//                newData.add("some new data");
//                newData.add("some new data");
//                newData.add("some new data");
//
                  displayResultRecycleview(RealData);
//                SwipeDeckAdapter adapter = new SwipeDeckAdapter(newData, context);
//                cardStack.setAdapter(adapter);
                  adapter.notifyDataSetChanged();

                  //Return Position To Zero
                  pos=0;

            }
        });


        //Like button
        setLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos==-1){
                    Snackbar snackbar = Snackbar.make(parent, R.string.No_cards, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {

                    Log.w("what", String.valueOf(RealData.get(pos).getName()));
                    if(db.CheckIsDataAlreadyInDBorNotLike(RealData.get(pos).getId())){
                        //message to user
                        Snackbar snackbar = Snackbar.make(parent, R.string.likealready, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else {
                        //send request
                        requestLike(RealData.get(pos).getId());

                    }

                }

            }
        });



        //Fav Button
        setFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos==-1){
                    Snackbar snackbar = Snackbar.make(parent, R.string.No_cards, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    // Log.w("IDItem", String.valueOf(getItem(pos).getId())+"   "+getItem(pos).getName());
                    Log.w("nameItem", String.valueOf(RealData.get(pos).getName())+"  "+RealData.get(pos).getId());
                    // Log.w("whatnewItem", String.valueOf(cardStack.getSelectedView().get));

                    addFav(RealData.get(pos).getId());
                }

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


    }

    private void getAllIntent() {

        Intent intent = getIntent();
        Type_Activity = intent.getStringExtra("TypeActivity");

        if(Type_Activity.equals("Manual_Activity")){

            Ram= intent.getStringExtra("RAM");
            Rom= intent.getStringExtra("ROM");
            o_software=intent.getStringExtra("OS");
            battery= intent.getStringExtra("BATTERY");
            processor=intent.getStringExtra("PROCESSOR");
            screen= intent.getStringExtra("SCREEN");

            //send Request
            requestManualSearch(Ram,Rom,battery,screen,processor,o_software);

        }else if(Type_Activity.equals("MainActivity")){

            priceRange = intent.getStringExtra("priceRange");
            charName=intent.getStringExtra("charName");


            //send Request
            callPriceRequest();

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
        public ProductModel getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.cardiremmobile, parent, false);
            }


            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            ImageView imageView = (ImageView) v.findViewById(R.id.mobileimage);
            if (!data.get(position).getImg().isEmpty())
            {
                final RotateLoading rotateLoading=v.findViewById(R.id.rotateloading);
                rotateLoading.start();
                Picasso.with(context).load(data.get(position).getImg()).error(R.drawable.ic_smartphone_empty).fit().centerCrop().into(imageView ,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        rotateLoading.stop();
                    }

                    @Override
                    public void onError() {
                        rotateLoading.stop();
                    }
                });
            }
            else
            {
                imageView.setImageResource(R.drawable.ic_smartphone_empty);
            }
            //String item = (String)getItem(position);
            TextView textView = (TextView) v.findViewById(R.id.mobilename);
            //String item = (String)getItem(position);
            if (!data.get(position).getName().isEmpty())
            {
                textView.setVisibility(View.VISIBLE);
                textView.setText(data.get(position).getName());
            }else {
                textView.setVisibility(View.GONE);
            }





            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hwardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    Intent i = new Intent(v.getContext(), CardSwipeSpecesActivity.class);
                    
                    i.putExtra("productModel",data.get(position));
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

    void requestManualSearch(String ram,String storage,String battery,String screen,String processor,String os){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                final ProgressDialog mProgressDialog = new ProgressDialog(swipcards.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                if(storage.equals("ROM/Storage")){
                    storage=null;
                }

                if(battery.equals("Battery")){
                    battery=null;
                }

                if(processor.equals("CPU")){
                    processor=null;
                }

                if(screen.equals("Screen")){
                    screen=null;
                }

                if(os.equals("Software/OS")){
                    os=null;
                }
                RamBodyModel ramBodyModel = new RamBodyModel(ram,storage,battery,screen,processor,os);
                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<List<ProductModel>> dataByValue = elBashaApi.getDataByValue3("application/x-www-form-urlencoded",ram,storage,battery,processor,os,screen);

                dataByValue.enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        if (response.isSuccessful())
                        {

                            imagemobileoops.setVisibility(View.GONE);
                            opstext.setVisibility(View.GONE);

                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))
                                displayResultRecycleview(response.body());
                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


                        }else {

                            imagemobileoops.setVisibility(View.VISIBLE);
                            opstext.setVisibility(View.VISIBLE);

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

                        imagemobileoops.setVisibility(View.VISIBLE);
                        opstext.setVisibility(View.VISIBLE);

                    }
                });


            }else {
                imagemobileoops.setVisibility(View.VISIBLE);
                opstext.setVisibility(View.VISIBLE);

                Snackbar snackbar = Snackbar.make(parent, R.string.Internet_not_access_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
                snackbar.show();

            }

        }else {
            imagemobileoops.setVisibility(View.VISIBLE);
            opstext.setVisibility(View.VISIBLE);

            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void displayResultRecycleview(List<ProductModel> listMobile) {

       /* testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");*/

        RealData =listMobile;

        if(listMobile.size()<0){
            imagemobileoops.setVisibility(View.VISIBLE);
            textnoproduct.setVisibility(View.VISIBLE);
        }else {
            imagemobileoops.setVisibility(View.GONE);
            textnoproduct.setVisibility(View.GONE);
        }

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
                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))
                            {
                                displayResultRecycleview(charFilter(response.body()));
                            }
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
                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))
                            {
                                displayResultRecycleview(charFilter(response.body()));
                            }
                            else
                            {
                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
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

                            imagemobileoops.setVisibility(View.GONE);
                            opstext.setVisibility(View.GONE);

                            Log.d("dahLog", String.valueOf(response.body().size()));
                            if (TextUtils.isEmpty(response.body().get(0).getError()))
                            {
                                displayResultRecycleview(charFilter(response.body()));
                            }
                            else
                            {

                                Snackbar snackbar = Snackbar.make(parent, R.string.there_are_no_products_right_now, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(parent, R.string.Failure_Please_try_again, Snackbar.LENGTH_LONG);
                            snackbar.show();

                            imagemobileoops.setVisibility(View.VISIBLE);
                            opstext.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        Snackbar snackbar = Snackbar.make(parent, R.string.Failure_Please_try_again, Snackbar.LENGTH_LONG);
                        snackbar.show();

                        imagemobileoops.setVisibility(View.VISIBLE);
                        opstext.setVisibility(View.VISIBLE);


                    }
                });
            }else {
                Snackbar snackbar = Snackbar.make(parent, R.string.Internet_not_access_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
                snackbar.show();

                imagemobileoops.setVisibility(View.VISIBLE);
                opstext.setVisibility(View.VISIBLE);

            }
        }else {
            Snackbar snackbar = Snackbar.make(parent, R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();

            imagemobileoops.setVisibility(View.VISIBLE);
            opstext.setVisibility(View.VISIBLE);

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


    public List<ProductModel> charFilter(List<ProductModel> modelList)
    {
        List<ProductModel> filteredList=new ArrayList<>();
        if (charName.equals("wa7shelprocessor"))
        {
            for (int i=0;i<modelList.size();i++)
            {
                ProductModel model=modelList.get(i);
                if (model.getMonster_of_processor()==1)
                {
                    filteredList.add(model);
                }
            }
        }
        else if (charName.equals("elfananeen"))
        {
            for (int i=0;i<modelList.size();i++)
            {
                ProductModel model=modelList.get(i);
                if (model.getArtists()==1)
                {
                    filteredList.add(model);
                }
            }
        }
        else if (charName.equals("superhero"))
        {
            for (int i=0;i<modelList.size();i++)
            {
                ProductModel model=modelList.get(i);
                if (model.getSuper_hero()==1)
                {
                    filteredList.add(model);
                }
            }
        }
        else if (charName.equals("mlookelselfy"))
        {
            for (int i=0;i<modelList.size();i++)
            {
                ProductModel model=modelList.get(i);
                if (model.getKings_of_selfie()==1)
                {
                    filteredList.add(model);
                }
            }
        }
        else if (charName.equals("mn8eerfslan"))
        {
            for (int i=0;i<modelList.size();i++)
            {
                ProductModel model=modelList.get(i);
                if (model.getWithout_stopping()==1)
                {
                    filteredList.add(model);
                }
            }
        }

        return filteredList;
    }


    //like request
    void requestLike(final long id){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                //Show message to user
                Snackbar snackbar = Snackbar.make(parent, R.string.like, Snackbar.LENGTH_LONG);
                snackbar.show();

                //save it of product
                db.insertProductIDLiked(id);

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<String> dataByValue = elBashaApi.like("application/x-www-form-urlencoded",id);

                dataByValue.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful())
                        {

                            Log.d("dahLoglike", String.valueOf(response.body()));
                            if (response.body().length()>2) {
                               /* Snackbar snackbar = Snackbar.make(parent, R.string.like, Snackbar.LENGTH_LONG);
                                snackbar.show();

                                //save it of product
                                db.insertProductIDLiked(id);*/
                            }



                        }else {

                            //Delete If falire
                            db.deleteLiketByID(id);
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //Delete If falire
                        db.deleteLiketByID(id);
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

    void addFav (Long id){
        if(db.CheckIsDataAlreadyInDBorNotFav(id)){
            Snackbar snackbar = Snackbar.make(parent, R.string.favalready, Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            //insert id in fav list
            db.insertFavProduct(id);
            Snackbar snackbar = Snackbar.make(parent, R.string.favadded, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


}


