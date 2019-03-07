package com.astalavista.saneesh.quizrrgame.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.astalavista.saneesh.quizrrgame.R;
import com.astalavista.saneesh.quizrrgame.Session;

public class ScoreBoardActivity extends BaseActivity {


    private TextView txtCorrect, txtWrong, txtSkipped, txtAccuracy, txtName;
    private ImageView imgViewProfile, imgViewBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        txtCorrect = (TextView) findViewById(R.id.txtCorrect);
        txtWrong = (TextView) findViewById(R.id.txtWrong);
        txtSkipped = (TextView) findViewById(R.id.txtSkipped);
        txtAccuracy = (TextView) findViewById(R.id.txtAccuracy);
        imgViewProfile = (ImageView) findViewById(R.id.imgViewProfile);
        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);
        txtName = (TextView) findViewById(R.id.txtName);

        txtCorrect.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtWrong.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtSkipped.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));

        txtCorrect.setText(String.valueOf(Session.getCorrectAns()));
        txtWrong.setText(String.valueOf(Session.getWrongAns()));
        txtSkipped.setText(String.valueOf(Session.getSkippedAns()));
        txtName.setText(Session.getUserName());

        float accuracy = 0;

        if (Session.getCorrectAns() == 0)
            accuracy = 0;
        else
            accuracy = Float.valueOf(Session.getCorrectAns()) / Float.valueOf(Session.getTotQuestions()) * 100;

        txtAccuracy.setText(Math.round(accuracy * 100) / 100 + "%");


//        new Random().nextInt(4) + 1


        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
