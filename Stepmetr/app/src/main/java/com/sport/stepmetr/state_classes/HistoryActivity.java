package com.sport.stepmetr.state_classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sport.stepmetr.MenuActivity;
import com.sport.stepmetr.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<String> arr = new ArrayList<String>();
    ArrayList<State> states = new ArrayList<State>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        LinearLayout linearLayout = findViewById(R.id.linear);
        //new DownloadImageTask(linearLayout).execute("http://159.69.90.204/api/Scorecounter/background.jpg");
        Glide.with(HistoryActivity.this)
                .load("http://159.69.90.204/api/stepCounter/background.jpg")
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
        switch (v.getId()) {
            case R.id.button10:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                prefs.edit().clear().commit();
                finish();
                startActivity(getIntent());
                break;
            case R.id.button11:
                Intent intent = new Intent(HistoryActivity.this, MenuActivity.class);
                startActivity(intent);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        arr = getArrayList("arr");
        if (arr != null) {
            for (String s : arr) {
                String[] words = s.split("_");
                setInitialData(words[0],words[1]);
            }
        }
        RecyclerView recyclerView = findViewById(R.id.list);
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }
    public void setInitialData(String score, String time){

        states.add(new State(score, time));


    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}