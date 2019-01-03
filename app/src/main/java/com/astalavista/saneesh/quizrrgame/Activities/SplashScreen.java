package com.astalavista.saneesh.quizrrgame.Activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import com.astalavista.saneesh.quizrrgame.R;
import com.astalavista.saneesh.quizrrgame.Session;


public class SplashScreen extends AppCompatActivity {

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgView = findViewById(R.id.imgView);
        Session.getSession(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;

                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                if (pref.getBoolean("login_executed", false)) {

                    intent = new Intent(SplashScreen.this, PreHomeActivity.class);

                } else {
                    intent = new Intent(SplashScreen.this, GameActivity.class);
                }

                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Pair pairs = new Pair<View, String>(imgView, "homeQuizzr");
                ActivityOptions activityOptions = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);

                }
                startActivity(intent, activityOptions.toBundle());
                finish();
            }
        }, 3000);

    }
}
