package com.ElBasha.mob.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;



public class CustomViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {

    String num0,num1,num2;

    public CustomViewPagerAdapter(FragmentManager fm,String num0,String num1,String num2) {
        super(fm);
        this.num0=num0;
        this.num1=num1;
        this.num2=num2;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if (position==0)
        {
            fragment=new introOneFragment();
            Bundle bundle = new Bundle();
            bundle.putString("num", num0);
            fragment.setArguments(bundle);
        }
        if (position==1)
        {
            fragment=new introOneFragment();
            Bundle bundle = new Bundle();
            bundle.putString("num", num1);
            fragment.setArguments(bundle);
        }
        if (position==2)
        {
            fragment=new introOneFragment();
            Bundle bundle = new Bundle();
            bundle.putString("num", num2);
            fragment.setArguments(bundle);
        }
return fragment;
    }

    @Override
    public int getCount() {
        if (TextUtils.isEmpty(num1))
        {
            return 1;
        }
        if (TextUtils.isEmpty(num2))
        {
            return 2;
        }
        return 3;
    }
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1 || position > 1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
            return;
        }

        if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            // Fade the page out.
            view.setAlpha(1 + position);
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            return;

        }

        if (position > 0.5 && position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(0);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);
            return;
        }
        if (position > 0.3 && position <= 0.5) { // (0,1]
            // Fade the page out.
            view.setAlpha(1);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * position);

            float scaleFactor = MIN_SCALE;
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            return;
        }
        if (position <= 0.3) { // (0,1]
            // Fade the page out.
            view.setAlpha(1);
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * position);

            // Scale the page down (between MIN_SCALE and 1)
            float v = (float) (0.3 - position);
            v = v >= 0.25f ? 0.25f : v;
            float scaleFactor = MIN_SCALE + v;
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
    }
}
