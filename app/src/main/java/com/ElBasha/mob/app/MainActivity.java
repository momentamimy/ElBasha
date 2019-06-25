package com.ElBasha.mob.app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.util.Log;
import android.widget.ImageView;

import com.ElBasha.mob.app.Controller.LocaleHelper;

public class MainActivity extends AppCompatActivity {

    String priceRange ;
    ImageView back;
    CardView wa7shelprocessor,elfananeen,superhero,mlookelselfy,mn8eerfslan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntnet();
        Log.w("aaaa",priceRange);


        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntnet() {
        Intent intent = getIntent();
        priceRange = intent.getStringExtra("priceRange");

        setTitle("Activity 3");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);

            fade.excludeTarget(android.R.id.navigationBarBackground, true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(fade);
                getWindow().setExitTransition(fade);
            }
        }

        wa7shelprocessor=findViewById(R.id.wa7shelprocessor);
        elfananeen=findViewById(R.id.elfananeen);
        superhero=findViewById(R.id.superhero);
        mlookelselfy=findViewById(R.id.mlookelselfy);
        mn8eerfslan=findViewById(R.id.mn8eerfslan);

        wa7shelprocessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair=new Pair<View,String>(wa7shelprocessor,"backgroundTransition");
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
                }
                Intent intent=new Intent(getApplicationContext(),Wa7shProccesseorTutorial.class);
                intent.putExtra("charName","wa7shelprocessor");
                startActivity(intent,options.toBundle());
            }
        });

        elfananeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair=new Pair<View,String>(elfananeen,"backgroundTransition");
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
                }
                Intent intent=new Intent(getApplicationContext(),Wa7shProccesseorTutorial.class);
                intent.putExtra("charName","elfananeen");
                startActivity(intent,options.toBundle());
            }
        });

        superhero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair=new Pair<View,String>(superhero,"backgroundTransition");
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
                }
                Intent intent=new Intent(getApplicationContext(),Wa7shProccesseorTutorial.class);
                intent.putExtra("charName","superhero");
                startActivity(intent,options.toBundle());
            }
        });

        mlookelselfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair=new Pair<View,String>(mlookelselfy,"backgroundTransition");
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
                }
                Intent intent=new Intent(getApplicationContext(),Wa7shProccesseorTutorial.class);
                intent.putExtra("charName","mlookelselfy");
                startActivity(intent,options.toBundle());
            }
        });

        mn8eerfslan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair=new Pair<View,String>(mn8eerfslan,"backgroundTransition");
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
                }
                Intent intent=new Intent(getApplicationContext(),Wa7shProccesseorTutorial.class);
                intent.putExtra("charName","mn8eerfslan");
                startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
