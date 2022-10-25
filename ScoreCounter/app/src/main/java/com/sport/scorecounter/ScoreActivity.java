package com.sport.scorecounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ScoreActivity extends AppCompatActivity  {
    private String TAG = "MainActivity: ";
    ArrayList<String> arr = new ArrayList<String>();
    private TextView textView;
    private Button buttonStart,buttonStop;

    private long mlCount = 0; // Счетчик времени
    private int mSecRate = 10; // обновлять каждые 10 мс
    private String timeShow = "";
    private Timer mTimer1;
    private TimerTask mTask1;

    TextView textViewLeft, textViewRight;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textView = (TextView) findViewById(R.id.text_id);
        textViewLeft = (TextView) findViewById(R.id.textView2);
        textViewRight = (TextView) findViewById(R.id.textView4);
        LinearLayout linearLayout = findViewById(R.id.linear);
        //new DownloadImageTask(linearLayout).execute("http://159.69.90.204/api/Scorecounter/background.jpg");
        Glide.with(ScoreActivity.this)
                .load("http://159.69.90.204/api/Scorecounter/background.jpg")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    public void onClick(View v) {
        int scoreL;
        int scoreR;
        switch (v.getId()) {
            case R.id.button:
                scoreL = Integer.parseInt(String.valueOf(textViewLeft.getText()));
                scoreL += 3;
                textViewLeft.setText(String.valueOf(scoreL));
                //+3
                break;
            case R.id.button2:
                scoreL = Integer.parseInt(String.valueOf(textViewLeft.getText()));
                scoreL += 2;
                textViewLeft.setText(String.valueOf(scoreL));
                //+2
                break;
            case R.id.button3:
                scoreL = Integer.parseInt(String.valueOf(textViewLeft.getText()));
                scoreL += 1;
                textViewLeft.setText(String.valueOf(scoreL));
                //+1
                break;
            case R.id.button4:
                scoreR = Integer.parseInt(String.valueOf(textViewRight.getText()));
                scoreR += 1;
                textViewRight.setText(String.valueOf(scoreR));
                //+1
                break;
            case R.id.button5:
                scoreR = Integer.parseInt(String.valueOf(textViewRight.getText()));
                scoreR += 2;
                textViewRight.setText(String.valueOf(scoreR));
                //+2
                break;
            case R.id.button6:
                scoreR = Integer.parseInt(String.valueOf(textViewRight.getText()));
                scoreR += 3;
                textViewRight.setText(String.valueOf(scoreR));
                //+3
                break;
            case R.id.button7:
                if (getArrayList("arr") != null) {
                    arr = getArrayList("arr");
                }
                arr.add(textViewLeft.getText() +
                        ":" +
                        textViewRight.getText() +
                        "_" +
                        textView.getText());
                saveArrayList(arr, "arr");
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mlCount = 0;
        startWatch();
    }

    // запускаем таймер
    private void startWatch() {
        if (mTimer1 == null && mTask1 == null) {
            mTimer1 = new Timer();
            mTask1 = new TimerTask() {
                @Override
                public void run() {
                    Message message = mHandler.obtainMessage(1);
                    mHandler.sendMessage(message);
                }
            };
            mTimer1.schedule (mTask1, 0, mSecRate); // обновление 10 мс
        }

    }


    /**
     * Таймер
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    mlCount++;
                    judgeTimeShow(mlCount);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    // Остановить обновление дисплея
    private void stopTimeShow() {
        if (mTimer1 != null) {
            mTimer1.cancel();
            mTimer1 = null;
        }
        if (mTask1 != null) {
            mTask1.cancel();
            mTask1 = null;
        }
    }

    // Отображение суждения
    private String  judgeTimeShow(long mlCount) {
        String str ="";
        long min = 0;
        long sec = 0;
        long mSec = 0;
        if (mlCount <=0) {
            str = "00:00:00";
        } else {
            sec = mlCount / (1000 / mSecRate); // вычисляется из миллисекунд
            if (sec < 60) {
                mSec = mlCount% (1000 / mSecRate); // Оставшиеся миллисекунды
            } else {
                min = sec / 60; // Рассчитать мин по секундам
                if (min > 99) {
                    str = "99:59:59";
                    textView.setText(str);
                    return str;
                }
                sec = sec % 60;
                mSec = mlCount - (min * 60 * (1000/mSecRate)) - (sec * (1000/mSecRate));
            }

            str = judgeSingleNum(min) + ":"+ judgeSingleNum(sec) + ":"+ judgeSingleNum(mSec);
        }

        Log.i (TAG, "Время равно mlCount:" + mlCount + "\n" + "Установленное время: + стр");
        textView.setText(str);
        return str;
    }

    // Определяем, добавлять ли 0
    private String judgeSingleNum(long mlCount) {
        String strData = "";
        if (mlCount < 10) {
            strData = "0" + mlCount;
        } else {
            strData = mlCount + "";
        }
        return strData;
    }

}