package com.sport.boxcounter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;


import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arr = new ArrayList<String>();
    EditText chooseTime, chooseTime2, chooseTime3, repCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.linear);
        Glide.with(MainActivity.this)
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
        arr.add("00:20");
        arr.add("03:00");
        arr.add("01:00");
        arr.add("3");
        chooseTime = findViewById(R.id.etChooseTime1);
        chooseTime2 = findViewById(R.id.etChooseTime2);
        chooseTime3 = findViewById(R.id.etChooseTime3);
        repCount = findViewById(R.id.etChooseTime4);
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("__:__");
        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
                MaskImpl.createTerminated(slots)
        );

        formatWatcher.installOn(chooseTime);
        formatWatcher.installOn(chooseTime2);
        formatWatcher.installOn(chooseTime3);


    }

    public void onClick(View v) {
        if (!String.valueOf(chooseTime.getText()).equals("")) {
            arr.set(0, String.valueOf(chooseTime.getText()));
        }
        if (!String.valueOf(chooseTime2.getText()).equals("")) {
            arr.set(1, String.valueOf(chooseTime2.getText()));
        }
        if (!String.valueOf(chooseTime3.getText()).equals("")) {
            arr.set(2, String.valueOf(chooseTime3.getText()));
        }
        if (!String.valueOf(repCount.getText()).equals("")) {
            arr.set(3, String.valueOf(repCount.getText()));
        }

        saveArrayList(arr, "arr");
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        startActivity(intent);
    }


    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }


}