package com.sport.footballcourse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sport.footballcourse.about_classes.AboutActivity;

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

public class MainActivity extends AppCompatActivity {
    ArrayList<MenuState> states = new ArrayList<MenuState>();
    ProgressBar mProgressBar;
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        linear = findViewById(R.id.linear_menu);
        Glide.with(MainActivity.this)
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

    private void setInitialData(String header, String img, String url) {

        states.add(new MenuState(header,url,img));
    }


    void run() throws IOException {
        mProgressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/footballCourse/menu.json")
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
                        mProgressBar.setVisibility(View.GONE);
                        System.out.println(myResponse);
                        try {
                            states = new ArrayList<MenuState>();
                            JSONObject jsonObject = new JSONObject(myResponse);
                            JSONArray arr = new JSONArray(jsonObject.optString("menu"));
                            for (int i = 0 ; i< arr.length(); i++) {
                                System.out.println(arr.getJSONObject(i).optString("header"));
                                setInitialData(arr.getJSONObject(i).optString("header"),
                                        arr.getJSONObject(i).optString("img"),
                                        arr.getJSONObject(i).optString("url"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RecyclerView recyclerView = findViewById(R.id.list);
                        // create adapter
                        MenuStateAdapter adapter = new MenuStateAdapter(MainActivity.this, states);
                        // install adapter for list
                        recyclerView.setAdapter(adapter);
                        mProgressBar.setVisibility(View.GONE);





                    }
                });

            }
        });
    }



}