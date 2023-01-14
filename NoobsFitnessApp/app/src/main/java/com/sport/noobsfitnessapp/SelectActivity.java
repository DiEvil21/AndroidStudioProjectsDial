package com.sport.noobsfitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SelectActivity extends AppCompatActivity {
    ArrayList<SelectState> states = new ArrayList<SelectState>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        LinearLayout linear = findViewById(R.id.linear_select);
        Glide.with(SelectActivity.this)
                .load("http://159.69.90.204/api/NoobsFitnessApp/img/background_main.png")
                .into(new CustomTarget<Drawable>() {
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
    public void setInitialData(String img, String header, String about, String url){

        states.add(new SelectState (header, about,img, url));


    }




    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/NoobsFitnessApp/select.json")
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
                            System.out.println(jsonObject);
                            JSONArray arr = new JSONArray(jsonObject.optString("progs"));
                            // заполняем states
                            for (int i = 0; i< arr.length(); i++) {
                                System.out.println(arr.getJSONObject(i).optString("name"));
                                setInitialData(arr.getJSONObject(i).optString("img"),
                                        arr.getJSONObject(i).optString("header"),
                                        arr.getJSONObject(i).optString("about"),
                                        arr.getJSONObject(i).optString("url"));
                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(SelectActivity.this));
                            // создаем адаптер
                            SelectStateAdapter adapter = new SelectStateAdapter(SelectActivity.this, states);
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