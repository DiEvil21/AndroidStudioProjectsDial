package ru.dievil.cleckerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_NAME = "myname";
    public static final String APP_PREFERENCES_SCORE = "myscore";
    public static final String APP_PREFERENCES_MN = "mymn";
    public static final String APP_PREFERENCES_P = "myp";
    SharedPreferences mSettings;
    Animation anim;
    SharedPreferences.Editor editor;
    int score;
    int mn,p;
    TextView scoreTextView, mnTextView;
    Button btn1, btn2;
    TextView textView1, textView2;
    ShopDialog shop;
    static MediaPlayer mediaPlayer;
    private SoundPool mSound;

    private int mMelody=1;
    private int mPlay;
    ImageButton btn;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.backgroundlong);
        mPlayer.setLooping(true);
        mPlayer.start();


        textView1 = findViewById(R.id.textView8);
        textView2 = findViewById(R.id.textView6);
        mnTextView = findViewById(R.id.textView7);
        anim = AnimationUtils.loadAnimation(this,R.anim.textview_anim);
        btn = findViewById(R.id.imageButton);
        scoreTextView = findViewById(R.id.textView3);
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        if (!mSettings.getString("myscore","").equals("")) {
            score = Integer.parseInt(mSettings.getString("myscore",""));
        }else {
            score = 0;
        }
        btn.setImageResource(R.drawable.ball);
        btn.setSoundEffectsEnabled(false);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn.setImageResource(R.drawable.ball_pressed);
                    score += 1 * mn +p;
                    mPlayer.setLooping(true);
                    mnTextView.setText("*"+mn +"+" +p);
                    mnTextView.startAnimation(anim);
                    scoreTextView.setText("" + score);
                    editor.putString("myscore", "" + score);
                    editor.apply();


                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn.setImageResource(R.drawable.ball);
                }
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!mSettings.getString("myscore","").equals("")) {
            score = Integer.parseInt(mSettings.getString("myscore",""));
        }else {
            score = 0;
        }
        scoreTextView.setText("" + score);
        if (!mSettings.getString("mymn","").equals("")) {
            mn = Integer.parseInt(mSettings.getString("mymn",""));
        }else {
            mn = 1;
        }
        if (!mSettings.getString("myp","").equals("")) {
            p = Integer.parseInt(mSettings.getString("myp",""));
        }else {
            p = 0;
        }
        textView1.setText("" + p*100);
        textView2.setText("" + mn*1000);

    }

    public void onCLick(View v) {
        switch (v.getId()) {
            case R.id.shop:
                if (score >= p*100) {
                    score -= p*100;
                    p=p+1;
                    textView1.setText("" + p*100);
                    scoreTextView.setText("" + score);
                    editor.putString("myp", "" + p);
                    editor.apply();
                }
                break;
            case R.id.players:
                if (score >= mn*1000) {
                    score -= mn*1000;
                    mn=mn+1;
                    textView2.setText("" + mn*1000);
                    scoreTextView.setText("" + score);
                    editor.putString("mymn", "" + mn);
                    editor.apply();
                }
                break;

        }

    }


}