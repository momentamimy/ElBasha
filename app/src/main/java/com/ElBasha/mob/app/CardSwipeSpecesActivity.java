package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Model.Database_Helper;
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.ElBasha.mob.app.Retrofit.retrofitHead;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CardSwipeSpecesActivity extends AppCompatActivity {

    ImageView close,fav_list;

    ImageView productPicture;
    TextView productName;
    TextView shopNowButton;
    TextView productStorage_Ram,productPrice;
    TextView productDisplay,productPlatform,productCamera,productBattery,productOtherFeatures,productReview,youtubeLink;


    LinearLayout colorLayout;
    FloatingActionButton color1,color2,color3,color4;
    ProductModel model;
    Database_Helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_swipe_speces);

        Intent intent = getIntent();
        model = intent.getParcelableExtra("productModel");

        db=new Database_Helper(this);

        productPicture=findViewById(R.id.phone_photo);
        productName=findViewById(R.id.phone_name);
        productStorage_Ram=findViewById(R.id.Storage_Ram);
        productPrice=findViewById(R.id.Price);
        productDisplay=findViewById(R.id.display);
        productPlatform=findViewById(R.id.platform);
        productCamera=findViewById(R.id.camera);
        productBattery=findViewById(R.id.battery);
        productOtherFeatures=findViewById(R.id.other_feature);
        productReview=findViewById(R.id.review);
        shopNowButton=findViewById(R.id.shop_now);
        youtubeLink=findViewById(R.id.YoutubeLink);

        colorLayout=findViewById(R.id.color_layout);
        color1=findViewById(R.id.color1);
        color2=findViewById(R.id.color2);
        color3=findViewById(R.id.color3);
        color4=findViewById(R.id.color4);



        fillDataModel(model);

        fav_list=findViewById(R.id.openfavList);
        close=findViewById(R.id.back);

        //open favourite list activity
        fav_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), favourite_list.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getApplicationContext(), ManualOrCharacterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);*/
                finish();
            }
        });
    }




    public void fillDataModel(final ProductModel model)
    {
        if (!model.getImg().isEmpty())
        {
            final RotateLoading rotateLoading=findViewById(R.id.rotateloading);
            rotateLoading.start();
            Picasso.with(getApplicationContext()).load(model.getImg())
                    .error(R.drawable.ic_warning).fit().centerCrop().into(productPicture,new com.squareup.picasso.Callback() {
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
            productPicture.setImageResource(R.drawable.ic_warning);
        }

        productName.setText(model.getName());
        productDisplay.setText(model.getScreen());
        productPlatform.setText(model.getOs());
        productCamera.setText(model.getCamera());
        productBattery.setText(model.getBattery());
        productOtherFeatures.setText(model.getFeatures());
        youtubeLink.setText(model.getYoutubeLink());
        //productReview.setText(model.getStorage()+" / "+model.getMyPrice()+" / "+model.getRam()) ;
        if (!model.getStorage().isEmpty()&&!model.getRam().isEmpty())
        {
            productStorage_Ram.setText(model.getStorage()+" & "+model.getRam());
        }
        if (!model.getMyPrice().isEmpty())
        {
            productPrice.setText(model.getMyPrice());
        }

        if (!model.getColor1().isEmpty())
        {
            color1.setVisibility(View.VISIBLE);
            color1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(model.getColor1())));
        }
        if (!model.getColor2().isEmpty())
        {
            color2.setVisibility(View.VISIBLE);
            color2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(model.getColor2())));
        }
        if (!model.getColor3().isEmpty())
        {
            color3.setVisibility(View.VISIBLE);
            color3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(model.getColor3())));
        }
        if (!model.getColor4().isEmpty())
        {
            color4.setVisibility(View.VISIBLE);
            color4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(model.getColor4())));
        }

        shopNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elbashastores.com/product/"+model.getName().replaceAll(" ","-")));
                startActivity(browserIntent);
            }
        });
        youtubeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getYoutubeLink()));
                startActivity(browserIntent);
            }
        });
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public void back(View view) {
        finish();
    }

    public void like(View view) {
        Log.w("what", String.valueOf(model.getName()));
        if(db.CheckIsDataAlreadyInDBorNotLike(model.getId())){
            //message to user
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), R.string.likealready, Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            //send request
            requestLike(model.getId());

        }
    }

    //like request
    void requestLike(final long id){
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                //Show message to user
                Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), R.string.like, Snackbar.LENGTH_LONG);
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
                Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), R.string.Internet_not_access_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
                snackbar.show();

            }

        }else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), R.string.You_are_offline_Please_connect_to_the_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    public void addFav (Long id){
        if(db.CheckIsDataAlreadyInDBorNotFav(id)){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), R.string.favalready, Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            //insert id in fav list
            db.insertFavProduct(id);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), R.string.favadded, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public void love(View view) {
        addFav(model.getId());
    }
}
