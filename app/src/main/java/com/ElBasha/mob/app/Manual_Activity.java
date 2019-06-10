package com.ElBasha.mob.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Manual_Activity extends AppCompatActivity {

    Spinner spinner,spinnerROM,spinnerCPU,spinnerBattery,spinnerScreen,spinnerSoftware;
    ImageView fav_list,back;
    Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_);

        fav_list=findViewById(R.id.favoratelist);
        searchbtn=findViewById(R.id.searchbtn);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //search btn and get result
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manual_Activity.this, swipcards.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        //open favourite list activity
        fav_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), favourite_list.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        spinner=(Spinner) findViewById(R.id.spinner);  // connect 1st spinner
        spinnerROM=(Spinner) findViewById(R.id.spinnerRom);  // connect 1st spinner
        spinnerCPU=(Spinner) findViewById(R.id.spinnerCPU);  // connect 1st spinner
        spinnerBattery=(Spinner) findViewById(R.id.spinnerBattery);  // connect 1st spinner
        spinnerScreen=(Spinner) findViewById(R.id.spinnerScreen);  // connect 1st spinner
        spinnerSoftware=(Spinner) findViewById(R.id.spinnerOS);  // connect 1st spinner

        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(this, R.layout.spinner_item_text,getResources().getStringArray(R.array.Ram));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }
        });


        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter2= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,getResources().getStringArray(R.array.Storage));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerROM.setAdapter(myAdapter2);
        spinnerROM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerROM.setSelection(0);
            }
        });


        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter3= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,getResources().getStringArray(R.array.Processor));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCPU.setAdapter(myAdapter3);
        spinnerCPU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerCPU.setSelection(0);
            }
        });


        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter4= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,getResources().getStringArray(R.array.Battery));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBattery.setAdapter(myAdapter4);
        spinnerBattery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerBattery.setSelection(0);
            }
        });

        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter5= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,getResources().getStringArray(R.array.software));
        myAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSoftware.setAdapter(myAdapter5);
        spinnerSoftware.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerSoftware.setSelection(0);
            }
        });


        //******************************************************ArrayAdapter for first spinner**********************************************************************
        ArrayAdapter<String> myAdapter6= new ArrayAdapter<String>(this,  R.layout.spinner_item_text,getResources().getStringArray(R.array.screen));
        myAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerScreen.setAdapter(myAdapter6);
        spinnerScreen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#083c93"));
                //((TextView) parent.getChildAt(0)).setTextSize(18);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerScreen.setSelection(0);
            }
        });

    }
}
