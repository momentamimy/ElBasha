package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;

public class Wa7shProccesseorTutorial extends AppCompatActivity {


    ImageView back;
    RelativeLayout next;
    RelativeLayout cardLayout;
    ViewPager pager;
    ImageView scrollRight,scrollLeft;

    String priceRange;
    String charName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wa7sh_proccesseor_tutorial);

        getIntnet();

        setTitle("Activity 4");
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
        scrollRight=findViewById(R.id.scroll_right);
        scrollLeft=findViewById(R.id.scroll_left);

        pager=findViewById(R.id.charPager);
        CharViewPagerAdapter adapter=new CharViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setPageTransformer(true,adapter);

        cardLayout=findViewById(R.id.cardLayout);
        if (charName.equals("wa7shelprocessor"))
        {
            cardLayout.setBackgroundResource(R.drawable.card_bgd_1);
            pager.setCurrentItem(0);
        }
        else if (charName.equals("elfananeen"))
        {
            cardLayout.setBackgroundResource(R.drawable.card_bgd_2);
            pager.setCurrentItem(1);
        }
        else if (charName.equals("superhero"))
        {
            cardLayout.setBackgroundResource(R.drawable.card_bgd_3);
            pager.setCurrentItem(2);
        }
        else if (charName.equals("mlookelselfy"))
        {
            cardLayout.setBackgroundResource(R.drawable.card_bgd_4);
            pager.setCurrentItem(3);
        }
        else if (charName.equals("mn8eerfslan"))
        {
            cardLayout.setBackgroundResource(R.drawable.card_bgd_5);
            pager.setCurrentItem(4);
        }


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0)
                {
                    cardLayout.setBackgroundResource(R.drawable.card_bgd_1);
                }
                else if (position==1)
                {
                    cardLayout.setBackgroundResource(R.drawable.card_bgd_2);
                }
                else if (position==2)
                {
                    cardLayout.setBackgroundResource(R.drawable.card_bgd_3);
                }
                else if (position==3)
                {
                    cardLayout.setBackgroundResource(R.drawable.card_bgd_4);
                }
                else if (position==4)
                {
                    cardLayout.setBackgroundResource(R.drawable.card_bgd_5);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        scrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem() < pager.getRight())
                    pager.setCurrentItem(pager.getCurrentItem()+1,true);
            }
        });

        scrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() > pager.getLeft())
                    pager.setCurrentItem(pager.getCurrentItem()-1,true);
            }
        });

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wa7shProccesseorTutorial.this, swipcards.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("TypeActivity","MainActivity");
                intent.putExtra("priceRange",priceRange);
                intent.putExtra("charName",charName);
                startActivity(intent);
                finish();
            }
        });



    }


    private void getIntnet() {
        Intent intent = getIntent();
        priceRange = intent.getStringExtra("priceRange");
        charName=intent.getStringExtra("charName");
    }

        @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
