package com.sport.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sport.fitnessapp.list_classes.State;
import com.sport.fitnessapp.list_classes.StateAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();
    ArrayList<String> arr = new ArrayList<String>();
    int score = 0;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        textView = findViewById(R.id.textView);



    }

    @Override
    protected void onStart() {
        super.onStart();
        states = new ArrayList<State>();
        RecyclerView recyclerView = findViewById(R.id.list);
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        arr = getArrayList("arr");

        if (arr != null) {
            for (String s : arr) {
                String[] words = s.split("_");
                setInitialData(words[0],words[1],words[2],words[3] + " Ккал");
                score += Integer.parseInt(words[3]);
            }
        }


        textView.setText(score + "Ккал");


    }

    public void setInitialData(String uri, String date, String name,String kkal){

        states.add(new State (uri,date,name,kkal));


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


    public void onCLick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                prefs.edit().clear().commit();
                finish();
                startActivity(getIntent());
                break;
        }
    }
}