package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Retrofit.ProductModel;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

public class CardSwipeSpecesActivity extends AppCompatActivity {

    ImageView close,fav_list;

    ImageView productPicture;
    TextView productName;
    TextView productStorage_Ram,productPrice;
    TextView productDisplay,productPlatform,productCamera,productBattery,productOtherFeatures,productReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_swipe_speces);

        Intent intent = getIntent();
        ProductModel model = intent.getParcelableExtra("productModel");

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




    public void fillDataModel(ProductModel model)
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
        productOtherFeatures.setText(model.getProcessor());
        //productReview.setText(model.getStorage()+" / "+model.getMyPrice()+" / "+model.getRam()) ;
        if (!model.getStorage().isEmpty()&&!model.getRam().isEmpty())
        {
            productStorage_Ram.setText(model.getStorage()+" & "+model.getRam());
        }
        if (!model.getMyPrice().isEmpty())
        {
            productPrice.setText(model.getMyPrice()+"EGP");
        }
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
