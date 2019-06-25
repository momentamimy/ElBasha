package com.ElBasha.mob.app;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.appolica.flubber.Flubber;
import com.race604.drawable.wave.WaveDrawable;


public class SplashScreen extends AppCompatActivity {

    Animation downtoup,righttoleft,lefttoright;

    ImageView left_arm,right_arm;

    TextView Text,TextEn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setTitle("Activity 1");
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
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        righttoleft = AnimationUtils.loadAnimation(this,R.anim.righttoleft);

        Text=findViewById(R.id.esaal);
        TextEn=findViewById(R.id.esaal_en);
        TextEn.setAnimation(downtoup);
        Text.setAnimation(downtoup);

        left_arm=findViewById(R.id.left_arm);
        left_arm.setAnimation(lefttoright);

        right_arm=findViewById(R.id.right_arm);
        right_arm.setAnimation(righttoleft);






        WaveDrawable mWaveDrawable = new WaveDrawable(this,R.drawable.progress_logo);

// Use as common drawable
    /*    progress_logo=findViewById(R.id.progress_logo);

        ValueAnimator animator = ValueAnimator.ofFloat(1, 1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(0);
        animator.setDuration(5000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        mWaveDrawable.setIndeterminateAnimator(animator);
        mWaveDrawable.setIndeterminate(true);
        mWaveDrawable.setLevel(10);
        progress_logo.setBackground(mWaveDrawable);
*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(getApplicationContext(), IntroSliderActivity.class));
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();

            }
        }, 2000);

    }

    public void head_animation(ImageView imageView,long duration)
    {
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .interpolator(Flubber.Curve.LINEAR)
                .repeatCount(0)                              // Repeat once
                .duration(duration)                              // Last for 1000 milliseconds(1 second)
                .createFor(imageView)                             // Apply it to the view
                .start();                                    // Start it now
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
