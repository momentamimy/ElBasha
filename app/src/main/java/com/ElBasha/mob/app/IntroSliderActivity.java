package com.ElBasha.mob.app;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appolica.flubber.Flubber;
import com.ocnyang.pagetransformerhelp.cardtransformer.CascadingPageTransformer;

import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator;


public class IntroSliderActivity extends AppCompatActivity {


    ViewPager pager;
    ViewPagerIndicator viewPagerIndicator;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

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
        }

        pager=findViewById(R.id.introViewPager);
        CustomViewPagerAdapter adapter=new CustomViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setPageTransformer(true,adapter);
        viewPagerIndicator=findViewById(R.id.viewPagerIndicator);
        viewPagerIndicator.initWithViewPager(pager);

        skip=findViewById(R.id.SKIP);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
    }
}
