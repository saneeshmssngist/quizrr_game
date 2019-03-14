package com.astalavista.saneesh.quizrrgame.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.astalavista.saneesh.quizrrgame.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private RelativeLayout layoutMain;
    private Handler handler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
//
//
//        final Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_in);
//        final Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
//
//        layoutMain.startAnimation(fadeOut);
//
//        fadeOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//                setBackground();
//                layoutMain.startAnimation(fadeIn);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//
//        fadeIn.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//                layoutMain.startAnimation(fadeOut);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });

    }

    private void setBackground() {

        switch (new Random().nextInt(4) + 1) {
            case 1:
                layoutMain.setBackgroundResource(R.drawable.quizrr_bg_1);
                break;
            case 2:
                layoutMain.setBackgroundResource(R.drawable.quizrr_bg_2);
                break;
            case 3:
                layoutMain.setBackgroundResource(R.drawable.quizrr_bg_3);
                break;
            case 4:
                layoutMain.setBackgroundResource(R.drawable.quizrr_bg_4);
                break;
        }

    }


}
