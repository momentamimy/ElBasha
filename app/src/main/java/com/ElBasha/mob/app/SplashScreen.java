package com.ElBasha.mob.app;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;
import com.appolica.flubber.Flubber;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.race604.drawable.wave.WaveDrawable;


public class SplashScreen extends AppCompatActivity {

    Animation downtoup,righttoleft,lefttoright,zoom;

    ImageView left_arm,right_arm,basha;

    TextView Text,TextEn;

    FirebaseDatabase firebaseDatabase;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferences =getSharedPreferences("TomyaTarbola",MODE_PRIVATE);

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

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.w("hakha", String.valueOf(dataSnapshot.getValue(Boolean.class)));
                boolean b=dataSnapshot.getValue(Boolean.class);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("TomyaTarbola",b);
                editor.commit();

                if (!b)
                {
                    Intent intent=new Intent(getApplicationContext(), Locked_hakha.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        righttoleft = AnimationUtils.loadAnimation(this,R.anim.righttoleft);
        zoom = AnimationUtils.loadAnimation(this,R.anim.zoom_out_in);


        Text=findViewById(R.id.esaal);
        TextEn=findViewById(R.id.esaal_en);
        TextEn.setAnimation(downtoup);
        Text.setAnimation(downtoup);

        left_arm=findViewById(R.id.left_arm);
        left_arm.setAnimation(lefttoright);

        right_arm=findViewById(R.id.right_arm);
        right_arm.setAnimation(righttoleft);

        basha=findViewById(R.id.basha);
        basha.setAnimation(zoom);




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

                boolean b=preferences.getBoolean("TomyaTarbola",true);
                if (b)
                {
                    startActivity(new Intent(getApplicationContext(), IntroSliderActivity.class));
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), Locked_hakha.class));
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }

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
