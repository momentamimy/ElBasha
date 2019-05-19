package com.ElBasha.mob.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    String priceRange ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntnet();
        Log.w("aaaa",priceRange);
    }

    private void getIntnet() {
        Intent intent = getIntent();
        priceRange = intent.getStringExtra("priceRange");
    }
}
