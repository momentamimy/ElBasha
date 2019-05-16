package com.ElBasha.mob.app;

import android.animation.ValueAnimator;
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

import com.race604.drawable.wave.WaveDrawable;


public class SplashScreen extends AppCompatActivity {

    Animation downtoup,fadeout;

    TextView Text;
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
        fadeout = AnimationUtils.loadAnimation(this,R.anim.fadeout);

        Text=findViewById(R.id.esaal);

        Text.setAnimation(downtoup);

        WaveDrawable mWaveDrawable = new WaveDrawable(this,R.drawable.progress_logo);

// Use as common drawable
        ImageView imageView=findViewById(R.id.progress_logo);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(0);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        mWaveDrawable.setIndeterminateAnimator(animator);
        mWaveDrawable.setIndeterminate(true);
        imageView.setBackground(mWaveDrawable);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(getApplicationContext(), IntroSliderActivity.class));
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();

            }
        }, 3000);

    }
}
