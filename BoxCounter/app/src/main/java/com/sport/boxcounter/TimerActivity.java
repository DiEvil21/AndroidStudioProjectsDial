package com.sport.boxcounter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class TimerActivity extends AppCompatActivity {
    ArrayList<String> arr = new ArrayList<String>();
    ArrayList<String> arrColor = new ArrayList<String>();
    TextView tv_timer,tv_round;
    LinearLayout linearLayout;
    int i, count;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        LinearLayout linearLayout2 = findViewById(R.id.linear);
        Glide.with(TimerActivity.this)
                .load("http://159.69.90.204/api/Scorecounter/background.jpg")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayout2.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        tv_timer = findViewById(R.id.tv_timer);
        tv_round = findViewById(R.id.textView8);
        linearLayout = findViewById(R.id.linearL_timer);
        arrColor.add("#ffee17");
        arrColor.add("#ff3a17");
        arrColor.add("#38e1ff");
        arrColor.add("#b521ff");

    }

    @Override
    protected void onStart() {
        super.onStart();
        i = 0;
        count = 1;
        arr = getArrayList("arr");
        startTimer(arr.get(0));
    }
    public void startTimerCount() {
        if ( count < Integer.parseInt(arr.get(3)) ) {
                startTimer(arr.get(0));
                count +=1;
                tv_round.setText("Раунд: " + count);
        }
    }

    public void startTimer(String time) {
        //Создаем таймер обратного отсчета на 20 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):
        linearLayout.setBackgroundColor(Color.parseColor(arrColor.get(i)));
        i += 1;
        new CountDownTimer(timeToMillis(time), 1000) {

            //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
            public void onTick(long millisUntilFinished) {
                String strSeconds;
                long second = (millisUntilFinished / 1000) % 60;

                long minute = (millisUntilFinished / (1000 * 60)) % 60;
                if (second < 10) {
                    strSeconds = "0" + second;
                }
                else {
                    strSeconds = "" + second;

                }
                tv_timer.setText(minute + ":" + strSeconds );
            }
            //Задаем действия после завершения отсчета (высвечиваем надпись "Бабах!"):
            public void onFinish() {
                if (i<3 ) {
                    startTimer(arr.get(i));
                }else {
                    tv_timer.setText("Всё");
                    i = 0;
                    startTimerCount();
                }
            }}.start();
    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public long timeToMillis(String time) {
        //02:11
        String minutes = time.toCharArray()[0] + String.valueOf(time.toCharArray()[1]);
        String seconds = time.toCharArray()[3] + String.valueOf(time.toCharArray()[4]);
        long millis = Integer.parseInt(String.valueOf(minutes)) * 60000L + Integer.parseInt(seconds) * 1000L;
        return millis;
    }



}