package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ElBasha.mob.app.Controller.LocaleHelper;

public class CardSwipeSpecesActivity extends AppCompatActivity {

    ImageView close,fav_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_swipe_speces);

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
