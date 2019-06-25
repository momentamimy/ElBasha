package com.ElBasha.mob.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ElBasha.mob.app.Controller.LocaleHelper;

public class ManualOrCharacterActivity extends AppCompatActivity {

    RelativeLayout manualButton,charButton;
    ImageView back;
    TextView langList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_or_character);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        manualButton=findViewById(R.id.ManualButton);
        charButton=findViewById(R.id.CharButton);
        langList=findViewById(R.id.lang_list);

        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Manual_Activity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        charButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        langList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(ManualOrCharacterActivity.this, langList);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.en:
                                // item one clicked
                                updatelang("en");
                                return true;
                            case R.id.ar:
                                // item one clicked
                                updatelang("ar");
                                return true;


                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.lang_list);
                popupMenu.show();
            }
        });
    }

    private void restartActivity() {
        finish();
        Intent intent1 =new Intent(this, ManualOrCharacterActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }
    private void updatelang(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        restartActivity();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
