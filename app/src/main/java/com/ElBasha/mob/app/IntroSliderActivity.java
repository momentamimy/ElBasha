package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.ElBasha.mob.app.Retrofit.ELBashaApi;
import com.ElBasha.mob.app.Retrofit.IntroResponse;
import com.ElBasha.mob.app.Retrofit.retrofitHead;
import com.appolica.flubber.Flubber;
import com.ocnyang.pagetransformerhelp.cardtransformer.CascadingPageTransformer;
import com.victor.loading.rotate.RotateLoading;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class IntroSliderActivity extends AppCompatActivity {


    ViewPager pager;
    ViewPagerIndicator viewPagerIndicator;
    TextView skip;
    RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        rotateLoading=findViewById(R.id.rotateloading2);
        setTitle("Activity 2");
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


            pager=findViewById(R.id.introViewPager);
            viewPagerIndicator=findViewById(R.id.viewPagerIndicator);

        }



        skip=findViewById(R.id.SKIP);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManualOrCharacterActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        getIntro();
    }

    public void getIntro()
    {
        if (CheckNetworkConnection.hasInternetConnection(getApplicationContext())) {
            //Check internet Access
            if (ConnectionDetector.hasInternetConnection(getApplicationContext())) {

                Retrofit retrofit = retrofitHead.retrofitTimeOut();
                ELBashaApi elBashaApi = retrofit.create(ELBashaApi.class);
                Call<IntroResponse> introList = elBashaApi.getIntroList();

                rotateLoading.start();
                introList.enqueue(new Callback<IntroResponse>() {
                    @Override
                    public void onResponse(Call<IntroResponse> call, Response<IntroResponse> response) {
                        Log.d("Introoooooo",response.body().getNum0());

                        if (!TextUtils.isEmpty(response.body().getNum0())) {
                            rotateLoading.stop();
                            CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager(), response.body().getNum0(),
                                    response.body().getNum1(), response.body().getNum2());
                            pager.setAdapter(adapter);
                            pager.setPageTransformer(true, adapter);
                            viewPagerIndicator.initWithViewPager(pager);

                        }
                        else
                        {
                            CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager(), "تطبيق اسأل الباشا يساعدك على اختيار الموبايل المناسب لك","","");
                            pager.setAdapter(adapter);
                            pager.setPageTransformer(true, adapter);
                            viewPagerIndicator.initWithViewPager(pager);
                            rotateLoading.stop();
                        }
                    }

                    @Override
                    public void onFailure(Call<IntroResponse> call, Throwable t) {
                        Log.d("Introoooooo",t.getMessage());
                        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager(), "تطبيق اسأل الباشا يساعدك على اختيار الموبايل المناسب لك","","");
                        pager.setAdapter(adapter);
                        pager.setPageTransformer(true, adapter);
                        viewPagerIndicator.initWithViewPager(pager);
                        rotateLoading.stop();
                    }
                });
            }
            else
            {
                CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager(), "تطبيق اسأل الباشا يساعدك على اختيار الموبايل المناسب لك","","");
                pager.setAdapter(adapter);
                pager.setPageTransformer(true, adapter);
                viewPagerIndicator.initWithViewPager(pager);
                rotateLoading.stop();
            }
        }
        else
        {
            CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager(), "تطبيق اسأل الباشا يساعدك على اختيار الموبايل المناسب لك","","");
            pager.setAdapter(adapter);
            pager.setPageTransformer(true, adapter);
            viewPagerIndicator.initWithViewPager(pager);
            rotateLoading.stop();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
