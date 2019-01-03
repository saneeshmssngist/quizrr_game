package com.astalavista.saneesh.quizrrgame.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.astalavista.saneesh.quizrrgame.Model.QuizTable;
import com.astalavista.saneesh.quizrrgame.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class PreHomeActivity extends Activity {

    private LinearLayout layoutProgress;

    private EditText edtTxtName, edtTxtPhone;
    private Button btnEnter;
    private LinearLayout layoutSkip;
    private boolean doubleBackToExitPressedOnce = false;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_home);

        getViews();
        initControls();
        setDatas();

    }

    private void setDatas() {


        ArrayList<QuizTable> quizTables = new Gson.fromloadJSONFromAsset();

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        btnEnter = findViewById(R.id.btnEnter);
        layoutSkip = findViewById(R.id.layoutSkip);

    }

    private void initControls() {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(edtTxtName.getText().toString()) && !TextUtils.isEmpty(edtTxtPhone.getText().toString())) {

                            SharedPreferences.Editor edt = pref.edit();
                            edt.putBoolean("login_executed", true);
                            edt.commit();

                            Intent intent = new Intent(PreHomeActivity.this, GameActivity.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);

                } else {
                    Toast.makeText(PreHomeActivity.this, "Please enter fields or Skip", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("general_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
