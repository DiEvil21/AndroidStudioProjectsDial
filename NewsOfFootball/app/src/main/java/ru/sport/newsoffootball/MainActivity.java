package ru.sport.newsoffootball;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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
    ArrayList<Info> info = new ArrayList<Info>();
    String url = "http://159.69.90.204/api/newsOfFootball/champion.json";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView tv_head;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress_bar);
        tv_head = findViewById(R.id.tv_head);
        tv_head.setText(R.string.champions_league);
        try {
            getInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/newsOfFootball/img/background_horizontal.png")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            linear.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } else {
            // In portrait
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/newsOfFootball/img/background.png")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            linear.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }




    }
    public void onClick(View v) {
        info = new ArrayList<Info>();
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.but_champion:
                tv_head.setText(R.string.champions_league);
                url = "http://159.69.90.204/api/newsOfFootball/champion.json";
                break;
            case R.id.but_european:
                tv_head.setText(R.string.european_league);
                url = "http://159.69.90.204/api/newsOfFootball/european.json";
                break;
            case R.id.but_la:
                tv_head.setText(R.string.la_league);
                url = "http://159.69.90.204/api/newsOfFootball/la.json";
                break;
        }
        try {
            getInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInfo(String head, String date,String img,String text) {
        info.add(new Info(head,img,date,text));
    }


    void getInfo(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        //http://159.69.90.204/api/newsOfFootball/champion.json
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
                        System.out.println(myResponse);

                        try {
                            JSONObject jsonObject;
                            JSONArray arr = new JSONArray(myResponse);
                            for (int i = 0; arr.length() > i ; i++) {
                                jsonObject = new JSONObject(arr.get(i).toString());
                                setInfo(jsonObject.optString("head"),
                                        jsonObject.optString("date"),
                                        jsonObject.optString("img"),
                                        jsonObject.optString("text"));
                                System.out.println(jsonObject.optString("head"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        RecyclerView recyclerView = findViewById(R.id.list);

                        // создаем адаптер
                        InfoAdapter adapter = new InfoAdapter(MainActivity.this, info);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }

}