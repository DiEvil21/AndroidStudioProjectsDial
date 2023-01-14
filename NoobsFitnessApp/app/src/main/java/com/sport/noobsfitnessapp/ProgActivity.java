package com.sport.noobsfitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgActivity extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        LinearLayout linear = findViewById(R.id.linear_prog);
        Glide.with(ProgActivity.this)
                .load("http://159.69.90.204/api/NoobsFitnessApp/img/background_main.png")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linear.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setInitialData(String img, String name, String rep, String kol){

        states.add(new State (img,name,kol,rep));


    }


    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            // выбираем программу
                            JSONArray arr = new JSONArray(jsonObject.optString("prog"));
                            // заполняем states
                            for (int i = 0; i< arr.length(); i++) {
                                System.out.println(arr.getJSONObject(i).optString("name"));
                                setInitialData(arr.getJSONObject(i).optString("img"),
                                        arr.getJSONObject(i).optString("name"),
                                        arr.getJSONObject(i).optString("rep"),
                                        arr.getJSONObject(i).optString("kol"));
                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ProgActivity.this));
                            // создаем адаптер
                            StateAdapter adapter = new StateAdapter(ProgActivity.this, states);
                            // устанавливаем для списка адаптер
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

            }
        });
    }



}