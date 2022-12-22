package com.sport.footballcourse.about_classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sport.footballcourse.MainActivity;
import com.sport.footballcourse.R;

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

public class AboutActivity extends AppCompatActivity {
    ArrayList<AboutState> states = new ArrayList<AboutState>();
    LinearLayout linear;
    String url;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        linear = findViewById(R.id.linear_about);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Glide.with(AboutActivity.this)
                .load("http://159.69.90.204/api/footballCourse/img/background.png")
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

    public void setInitialData(String title, String text, String image){

        states.add(new AboutState (title,text,image));


    }
    void run() throws IOException {
        System.out.println(url);
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
                            states = new ArrayList<AboutState>();
                            JSONArray arr = new JSONArray(myResponse);
                            JSONObject jsonObject = arr.getJSONObject(1);
                            System.out.println("---------------------------------------------------");
                            System.out.println(arr.getJSONObject(0));
                            for (int i = 0 ; i < arr.length() ; i++) {
                                jsonObject = new JSONObject(arr.get(i).toString());
                                System.out.println(jsonObject.optString("tittle"));
                                setInitialData(jsonObject.optString("tittle"),
                                        jsonObject.optString("text"),
                                        jsonObject.optString("img"));
                                System.out.println("повезло----------------------------");


                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            // создаем адаптер
                            AboutStateAdapter adapter = new AboutStateAdapter(AboutActivity.this, states);
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