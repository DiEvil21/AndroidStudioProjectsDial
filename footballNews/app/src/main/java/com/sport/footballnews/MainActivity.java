package com.sport.footballnews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();
    String url;
    TextView tv_header;
    Button btn_la,btn_europe, btn_champ;
    LinearLayout linear;
    ProgressBar mProgressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url =  "http://159.69.90.204/api/footballNews/la_news.json";
        btn_champ = findViewById(R.id.btn_champ);
        btn_europe = findViewById(R.id.btn_europe);
        btn_la = findViewById(R.id.btn_la);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("Ла Лига");
        btn_la.setBackground(getDrawable(R.drawable.btn_background_active));
        btn_champ.setBackground(getDrawable(R.drawable.btn_background));
        btn_europe.setBackground(getDrawable(R.drawable.btn_background));
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        linear = findViewById(R.id.linear_main);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/footballNews/img/background.png")
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
    }

    public void setInitialData(String title, String date, Spanned text, String image){

        states.add(new State (title,date,text,image));


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onClick(View v) {
        states = new ArrayList<State>();
        switch (v.getId()) {
            case R.id.btn_champ:
                btn_champ.setBackground(getDrawable(R.drawable.btn_background_active));
                btn_la.setBackground(getDrawable(R.drawable.btn_background));
                btn_europe.setBackground(getDrawable(R.drawable.btn_background));
                tv_header.setText("Лига Чемпионов");
                url = "http://159.69.90.204/api/footballNews/champion_news.json";
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_la:
                btn_la.setBackground(getDrawable(R.drawable.btn_background_active));
                btn_champ.setBackground(getDrawable(R.drawable.btn_background));
                btn_europe.setBackground(getDrawable(R.drawable.btn_background));
                tv_header.setText("Ла Лига");
                url =  "http://159.69.90.204/api/footballNews/la_news.json";
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_europe:
                btn_europe.setBackground(getDrawable(R.drawable.btn_background_active));
                btn_champ.setBackground(getDrawable(R.drawable.btn_background));
                btn_la.setBackground(getDrawable(R.drawable.btn_background));
                tv_header.setText("Лига Европы");
                url = "http://159.69.90.204/api/footballNews/europe_news.json";
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        RecyclerView recyclerView = findViewById(R.id.list);
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(MainActivity.this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }



    void run() throws IOException {
        mProgressBar.setVisibility(View.VISIBLE);
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
                        mProgressBar.setVisibility(View.GONE);
                        String str = "";
                        Spanned news = Html.fromHtml("");
                        try {
                            states = new ArrayList<State>();
                            JSONObject jsonObject = new JSONObject(myResponse);
                            System.out.println(myResponse);
                            JSONArray arr = new JSONArray(jsonObject.optString("arr"));
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject json = new JSONObject(arr.get(i).toString());
                                JSONArray jArr = new JSONArray(json.optString("news"));
                                for (int n = 0; n < jArr.length(); n++) {
                                    JSONObject jsonObject1  = new JSONObject(jArr.get(n).toString());
                                    str = str + "<font color=#9e9e9e>" + jsonObject1.optString("time") + "</font> "+ jsonObject1.optString("text") +"<br><br>";
                                    if (jsonObject1.optString("text").equals("")) {
                                        str = "";
                                    }

                                }
                                news = Html.fromHtml(str);
                                setInitialData(json.optString("header"),json.optString("date"),news,json.optString("img"));
                                str = "";
                            }



                            RecyclerView recyclerView = findViewById(R.id.list);
                            // создаем адаптер
                            StateAdapter adapter = new StateAdapter(MainActivity.this, states);
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