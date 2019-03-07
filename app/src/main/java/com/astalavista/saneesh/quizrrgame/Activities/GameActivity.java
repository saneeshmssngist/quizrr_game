package com.astalavista.saneesh.quizrrgame.Activities;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.astalavista.saneesh.quizrrgame.Model.QuizTable;
import com.astalavista.saneesh.quizrrgame.R;
import com.astalavista.saneesh.quizrrgame.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtViewQuestion, txtViewOption1, txtViewOption2, txtViewOption3, txtViewOption4;

    private int questionNumber = -1;
    private QuizTable questionData;
    private LinearLayout layoutOption1, layoutOption2, layoutOption3, layoutOption4;
    private LinearLayout layoutButton;

    private TextView txtViewCoins, txtViewQuestionNumber;

    private Handler handler1, handler2, handlerTimer;

    private int myCoins, count;
//    private MediaPlayer mediaCoinDrop, mediaCountDown, mediaError;

    private MediaPlayer nextSlide, buttonClick, wrongAnswer, correctAnswer;
    private TextView txtViewTimeout;
    private int timerCount;

    private String[] quizItems;
    private int quizItemsCount = 0;
    private List<String> dupIds;

    private ImageView imgSound;
    private LinearLayout layoutButtonLeft;
    private SharedPreferences pref;
    private RelativeLayout layoutQuizrr;
    private Dialog dialog;
    private TextView txtNum1, txtNum2, txtNum3, txtNum4;
    private ImageView imgViewOptions;
    private RelativeLayout fiftyOption;

//    private AdView adMobView;
    // private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_quiz);

        handler1 = new Handler();
        handler2 = new Handler();
        handlerTimer = new Handler();

        Session.getSession(this);
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

//        setStatusBarGradiant(this);
        getViews();
        initControl();
        setData();

        setVolume();
        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
//        MobileAds.initialize(this, getResources().getString(R.string.APPID));
//
//        adMobView = (AdView) findViewById(R.id.adMobView);
//        adMobView.loadAd(new AdRequest.Builder().build());
//
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getResources().getString(R.string.INTERTITIAID));
//        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void getViews() {

        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewOption1 = (TextView) findViewById(R.id.txtViewOption1);
        txtViewOption2 = (TextView) findViewById(R.id.txtViewOption2);
        txtViewOption3 = (TextView) findViewById(R.id.txtViewOption3);
        txtViewOption4 = (TextView) findViewById(R.id.txtViewOption4);

        layoutOption1 = (LinearLayout) findViewById(R.id.layoutOption1);
        layoutOption2 = (LinearLayout) findViewById(R.id.layoutOption2);
        layoutOption3 = (LinearLayout) findViewById(R.id.layoutOption3);
        layoutOption4 = (LinearLayout) findViewById(R.id.layoutOption4);

        txtNum1 = (TextView) findViewById(R.id.txtNum1);
        txtNum2 = (TextView) findViewById(R.id.txtNum2);
        txtNum3 = (TextView) findViewById(R.id.txtNum3);
        txtNum4 = (TextView) findViewById(R.id.txtNum4);
        imgViewOptions = (ImageView) findViewById(R.id.imgView);

        txtViewQuestion.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum1.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum2.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum3.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum4.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption1.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption2.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption3.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption4.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));

        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);
        dupIds = new ArrayList<>();

        txtViewCoins = (TextView) findViewById(R.id.txtViewCoins);
        txtViewQuestionNumber = (TextView) findViewById(R.id.txtViewQuestionNumber);
        layoutQuizrr = (RelativeLayout) findViewById(R.id.activity_sample);

//        circleProgress = (CircleProgress) findViewById(R.id.circleProgress);
//        textViewTime = (TextView) findViewById(R.id.textViewTime);
        txtViewTimeout = (TextView) findViewById(R.id.txtViewTimeout);

        layoutButtonLeft = (LinearLayout) findViewById(R.id.layoutButtonLeft);

    }

    private void setVolume() {

        if (pref.getBoolean("SoundSystem", true)) {

            nextSlide.setVolume(1, 1);
            buttonClick.setVolume(1, 1);
            wrongAnswer.setVolume(1, 1);
            correctAnswer.setVolume(1, 1);
//            mediaCountDown.setVolume(1, 1);
//            mediaError.setVolume(1, 1);

        } else {

            nextSlide.setVolume(0, 0);
            buttonClick.setVolume(0, 0);
            wrongAnswer.setVolume(0, 0);
            correctAnswer.setVolume(0, 0);
//            mediaCountDown.setVolume(0, 0);
//            mediaError.setVolume(0, 0);
        }

    }

    public void initControl() {

        nextSlide = MediaPlayer.create(this, R.raw.next_slide);
        buttonClick = MediaPlayer.create(this, R.raw.button_click);
        wrongAnswer = MediaPlayer.create(this, R.raw.wrongsound);
        correctAnswer = MediaPlayer.create(this, R.raw.correctsound);
//        mediaCountDown = MediaPlayer.create(this, R.raw.sound2);
//        mediaError = MediaPlayer.create(this, R.raw.sound1);

        txtViewOption1.setOnClickListener(this);
        txtViewOption2.setOnClickListener(this);
        txtViewOption3.setOnClickListener(this);
        txtViewOption4.setOnClickListener(this);

        layoutButton.setOnClickListener(this);
        layoutButtonLeft.setOnClickListener(this);
        imgViewOptions.setOnClickListener(this);

    }

    public void setData() {

        txtViewCoins.setText(Session.getSharedData("myCoins"));
        setQuestionDatas();

    }

    private void setInterstitialAd() {

//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                setData();
//
//                interstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//        });
//
//        if (interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        } else {
//
//            setData();
//        }

    }

    public void setQuestionDatas() {

        //increase question number .
        questionNumber++;

        Session.setTotQuestions(questionNumber);

        questionData = SplashScreen.INSTANCE.myDao().getQuizQuestion();
        //check duplicate data
        checkDupdatas(questionData.getId());

        //set Question number.
        txtViewQuestionNumber.setText("#" + String.valueOf(questionNumber + 1));

        //clearing all datas .
        resetAllDatas();

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(500);

        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(500);

        txtViewQuestion.startAnimation(out);
        txtViewOption1.startAnimation(out);
        txtViewOption2.startAnimation(out);
        txtViewOption3.startAnimation(out);
        txtViewOption4.startAnimation(out);
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtViewQuestion.setText(questionData.getQuestion());
                txtViewQuestion.startAnimation(in);

                txtViewOption1.setText(questionData.getOption1());
                txtViewOption1.startAnimation(in);

                txtViewOption2.setText(questionData.getOption2());
                txtViewOption2.startAnimation(in);

                txtViewOption3.setText(questionData.getOption3());
                txtViewOption3.startAnimation(in);

                txtViewOption4.setText(questionData.getOption4());
                txtViewOption4.startAnimation(in);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        txtViewOption1.setText(questionData.getOption1());
        txtViewOption2.setText(questionData.getOption2());
        txtViewOption3.setText(questionData.getOption3());
        txtViewOption4.setText(questionData.getOption4());

        timerCount = 30;
//        mediaCountDown.start();
//        mediaCountDown.setLooping(true);
        // circleProgress.setFinishedColor(Color.parseColor("#3084ea"));
//        handlerTimer.post(runnableTimer);

    }

    private void setBackground() {

        switch (new Random().nextInt(4) + 1) {
            case 1:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_1);
                break;
            case 2:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_2);
                break;
            case 3:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_3);
                break;
            case 4:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_4);
                break;
        }

    }

    private void checkDupdatas(int id) {

        for (int i = 0; i < dupIds.size(); i++) {
            if (dupIds.get(i).equals(String.valueOf(id)))
                setQuestionDatas();
        }
    }


    Runnable runnableTimer = new Runnable() {
        @Override
        public void run() {

            timerCount--;
//            textViewTime.setText(String.valueOf(timerCount));

            int countSet = Integer.valueOf((int) (timerCount * 3.3333));
            // circleProgress.setProgress(countSet);

            if (timerCount == 5) {
//                mediaCountDown.set
                //     circleProgress.setFinishedColor(Color.parseColor("#d91a1d"));
            }

            if (timerCount == 0) {
                handlerTimer.removeCallbacks(runnableTimer);
                timerComplete();
                return;
            }

            handlerTimer.postDelayed(runnableTimer, 1000);
        }
    };

    private void timerComplete() {

//        mediaCountDown.pause();
//        mediaError.start();
        txtViewTimeout.setVisibility(View.VISIBLE);

        //for display correct answer.
        switch (Integer.valueOf(questionData.getAnswer())) {
            case 1:
                layoutOption1.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
            case 2:
                layoutOption2.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
            case 3:
                layoutOption3.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
            case 4:
                layoutOption4.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
        }

        layoutButton.setVisibility(View.VISIBLE);
        diableButtons();

    }


    public void resetAllDatas() {

        enableButtons();
        layoutButton.setVisibility(View.GONE);
        txtViewTimeout.setVisibility(View.GONE);

        layoutOption1.setVisibility(View.VISIBLE);
        layoutOption2.setVisibility(View.VISIBLE);
        layoutOption3.setVisibility(View.VISIBLE);
        layoutOption4.setVisibility(View.VISIBLE);

        layoutOption1.setBackgroundResource(R.drawable.game_quiz_options);
        layoutOption2.setBackgroundResource(R.drawable.game_quiz_options);
        layoutOption3.setBackgroundResource(R.drawable.game_quiz_options);
        layoutOption4.setBackgroundResource(R.drawable.game_quiz_options);

        txtNum1.setBackgroundResource(R.drawable.game_quiz_num);
        txtNum2.setBackgroundResource(R.drawable.game_quiz_num);
        txtNum3.setBackgroundResource(R.drawable.game_quiz_num);
        txtNum4.setBackgroundResource(R.drawable.game_quiz_num);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        buttonClick.start();

        switch (id) {
            case R.id.txtViewOption1:
                layoutOption1.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum1.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(1);
                break;
            case R.id.txtViewOption2:
                layoutOption2.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum2.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(2);
                break;
            case R.id.txtViewOption3:
                layoutOption3.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum3.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(3);
                break;
            case R.id.txtViewOption4:
                layoutOption4.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum4.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(4);
                break;
            case R.id.imgView:
                revealShowHome(0);
                break;
            case R.id.layoutButton:

//                if ((questionNumber + 1) % 10 == 0) {
//                    setInterstitialAd();
//                } else {
                //reveal animation .

//                }
                break;
//            case R.id.layoutButtonLeft:
//
//                SharedPreferences.Editor edt = pref.edit();
//
//                if (pref.getBoolean("SoundSystem", true)) {
//                    edt.putBoolean("SoundSystem", false);
//                    edt.commit();
//                    setVolume();
//                } else {
//                    edt.putBoolean("SoundSystem", true);
//                    edt.commit();
//                    setVolume();
//                }
//
//                break;
        }

    }

    private void checkAnswer(final int answerNum) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                handlerTimer.removeCallbacks(runnableTimer);
//        mediaCountDown.pause();

                //setting the coins ..
                setCoinsCount(answerNum);
//        setLOttie();

                //for display wrong answer .
                if (!questionData.getAnswer().equals(String.valueOf(answerNum))) {
                    wrongAnswer.start();
                    Session.setWrongAns(1);
                    switch (answerNum) {
                        case 1:
                            layoutOption1.setBackgroundResource(R.drawable.game_quiz_wrong);
                            txtNum1.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                        case 2:
                            layoutOption2.setBackgroundResource(R.drawable.game_quiz_wrong);
                            txtNum2.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                        case 3:
                            layoutOption3.setBackgroundResource(R.drawable.game_quiz_wrong);
                            txtNum3.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                        case 4:
                            layoutOption4.setBackgroundResource(R.drawable.game_quiz_wrong);
                            txtNum4.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                    }

                }

                //for display wrong answer .
                if (questionData.getAnswer().equals(String.valueOf(answerNum))) {
                    correctAnswer.start();
                    Session.setCorrectAns(1);
                }

                //for display correct answer.
                switch (Integer.valueOf(questionData.getAnswer())) {
                    case 1:
                        layoutOption1.setBackgroundResource(R.drawable.game_quiz_correct);
                        txtNum1.setBackgroundResource(R.drawable.game_quiz_num_right);
                        break;
                    case 2:
                        layoutOption2.setBackgroundResource(R.drawable.game_quiz_correct);
                        txtNum2.setBackgroundResource(R.drawable.game_quiz_num_right);
                        break;
                    case 3:
                        layoutOption3.setBackgroundResource(R.drawable.game_quiz_correct);
                        txtNum3.setBackgroundResource(R.drawable.game_quiz_num_right);
                        break;
                    case 4:
                        layoutOption4.setBackgroundResource(R.drawable.game_quiz_correct);
                        txtNum4.setBackgroundResource(R.drawable.game_quiz_num_right);
                        break;
                }


//        layoutButton.setVisibility(View.VISIBLE);
                diableButtons();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        revealShowHome(1);
                        nextSlide.start();

                    }
                }, 1500);

            }
        }, 1000);

    }

    public void diableButtons() {
        txtViewOption1.setEnabled(false);
        txtViewOption2.setEnabled(false);
        txtViewOption3.setEnabled(false);
        txtViewOption4.setEnabled(false);
    }

    public void enableButtons() {
        txtViewOption1.setEnabled(true);
        txtViewOption2.setEnabled(true);
        txtViewOption3.setEnabled(true);
        txtViewOption4.setEnabled(true);
    }

    public void setCoinsCount(int answerNum) {
        myCoins = Integer.parseInt(Session.getSharedData("myCoins"));
        count = 0;

        if (questionData.getAnswer().equals(String.valueOf(answerNum))) {


            Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
            txtViewCoins.startAnimation(zoomin);

//            mediaCoinDrop.start();

            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    count += 1;

                    if (count > 10) {
//                        mediaCoinDrop.pause();
                        Session.setSharedData("myCoins", String.valueOf(myCoins));
//                        Animation zoomout = AnimationUtils.loadAnimation(GameActivity.this, R.anim.zoomout);
//                        txtViewCoins.startAnimation(zoomout);
                        return;
                    }

                    myCoins += 1;
                    txtViewCoins.setText(String.valueOf(myCoins));
                    handler1.postDelayed(this, 100);

                }
            }, 100);
        } else {
//            mediaError.start();

            if (myCoins > 2) {

                Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
                txtViewCoins.startAnimation(zoomin);

                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        count += 1;
                        if (count > 5) {
                            Session.setSharedData("myCoins", String.valueOf(myCoins));
//                            Animation zoomout = AnimationUtils.loadAnimation(GameActivity.this, R.anim.zoomout);
//                            txtViewCoins.startAnimation(zoomout);
                            return;
                        }

                        myCoins -= 1;
                        txtViewCoins.setText(String.valueOf(myCoins));
                        handler2.postDelayed(this, 100);

                    }
                }, 100);

            }
        }
    }

    @Override
    public void onBackPressed() {
        handlerTimer.removeCallbacks(runnableTimer);

        Intent intent = new Intent(this, ScoreBoardActivity.class);
        startActivity(intent);

//
//        mediaCoinDrop.stop();
//        mediaCountDown.stop();
//        mediaError.stop();

    }

    @Override
    protected void onResume() {
        super.onResume();

//        mediaCoinDrop.setVolume(1, 1);
//        mediaCountDown.setVolume(1, 1);
//        mediaError.setVolume(1, 1);

    }

    @Override
    protected void onStop() {
        super.onStop();

//        mediaCoinDrop.setVolume(0, 0);
//        mediaCountDown.setVolume(0, 0);
//        mediaError.setVolume(0, 0);
    }

    public void setLOttie() {
//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//        animationView.setAnimation(String.valueOf(R.raw.sky));
//
//        animationView.playAnimation();

    }


    private View dView;

    private void revealShowHome(final int id) {

        if(dialog != null)
            dialog.dismiss();

        if (id == 1) {
            dView = LayoutInflater.from(GameActivity.this).inflate(R.layout.game_reveal_anim, null);
        } else {
            dView = LayoutInflater.from(GameActivity.this).inflate(R.layout.game_options_layout, null);;
        }
        dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    revealShow(dView, id);
                }
            });

            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK) {

                        revealShow(dView, id);
                        return true;
                    }

                    return false;
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.show();
    }

    private View viewss;

    private void revealShow(final View dialogView, final int id) {

        int w, h;

        if (id == 1) {
            viewss = dialogView.findViewById(R.id.reveallayout);
            w = 0;
            h = viewss.getHeight();
        } else {
            viewss = dialogView.findViewById(R.id.layoutMain);
            imgSound = dialogView.findViewById(R.id.imgSound);
            w = viewss.getWidth() / 2;
            h = viewss.getHeight();

            if (pref.getBoolean("SoundSystem", true))
                imgSound.setImageResource(R.drawable.ic_volume_up);
            else
                imgSound.setImageResource(R.drawable.ic_volume_off);

        }


        int endRadius = (int) Math.hypot(w, h);

        Animator revealAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            revealAnimator = ViewAnimationUtils.createCircularReveal(viewss, w, h, 0, endRadius);
        }

        viewss.setVisibility(View.VISIBLE);
        if (id == 1)
            revealAnimator.setDuration(1000);
        else
            revealAnimator.setDuration(500);

        revealAnimator.start();

        if (id == 1)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    setBackground();

                }
            }, 500);

        revealAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (id == 1) {
                    if (dialog != null)
                        dialog.dismiss();
                    setData();
                }

            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    public void fiftyOptionClick(View view) {

        int answer = Integer.valueOf(questionData.getAnswer());

        int first = answer - 1;
        int second = answer + 1;

        if (first == 0)
            hideOption(3);
        else
            hideOption(first);

        if (second == 5)
            hideOption(2);
        else
            hideOption(second);

    }

    private void hideOption(int first) {

        if (dialog != null)
            dialog.dismiss();

        switch (first) {
            case 1:
                layoutOption1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                layoutOption2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                layoutOption3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                layoutOption4.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void viewOptionClick(View view) {

        diableButtons();
        Session.setSkippedAns(1);
        if (dialog != null)
            dialog.dismiss();
        checkAnswer(Integer.valueOf(questionData.getAnswer()));
    }


    public void soundOptionClick(View view) {
        if (dialog != null)
            dialog.dismiss();

        SharedPreferences.Editor edt = pref.edit();

        if (pref.getBoolean("SoundSystem", true)) {
            edt.putBoolean("SoundSystem", false);
            edt.commit();
            setVolume();
            imgSound.setImageResource(R.drawable.ic_volume_off);
        } else {
            edt.putBoolean("SoundSystem", true);
            edt.commit();
            setVolume();
            imgSound.setImageResource(R.drawable.ic_volume_up);
        }

    }

    public void shareOptionClick(View view) {
        if (dialog != null)
            dialog.dismiss();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Infinity Quiz");

        String message = "Let me recommend you this application for psc preparation"
                + "https://play.google.com/store/apps/details?id=com.astalavista.saneesh.quizrrgame";

        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Choose one"));

    }

    public void optionsLayoutClick(View view)
    {
        if(dialog != null)
            dialog.dismiss();
    }

    public void reloadOptionClick(View view)
    {
        if(dialog != null)
            dialog.dismiss();
        setData();
    }

    public void profileOptionClick(View view)
    {
        handlerTimer.removeCallbacks(runnableTimer);
        startActivity(new Intent(this, ScoreBoardActivity.class));
    }


}
