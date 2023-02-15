package ru.sport.bayermapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
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
import ru.sport.bayermapp.newsAdapter.NewsState;
import ru.sport.bayermapp.newsAdapter.NewsStateAdapter;

public class NewsActivity extends AppCompatActivity {
    ArrayList<NewsState> states = new ArrayList<NewsState>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_news);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(NewsActivity.this)
                    .load("http://159.69.90.204/api/BayerMApp/img/pole_horizontal.png")
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
            Glide.with(NewsActivity.this)
                    .load("http://159.69.90.204/api/BayerMApp/img/pole.png")
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
        ImageView logo = findViewById(R.id.imageView);
        Glide.with(logo.getContext())
                .load("http://159.69.90.204/api/BayerMApp/img/logo.png")
                .into(logo);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInitialData(String title, String date, String text, String image){

        states.add(new NewsState (title,date,text,image));


    }



    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/BayerMApp/news.json")
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
                            states = new ArrayList<NewsState>();
                            JSONArray arr = new JSONArray(myResponse);
                            JSONObject jsonObject = arr.getJSONObject(1);
                            System.out.println("---------------------------------------------------");
                            System.out.println(arr.getJSONObject(0));
                            for (int i = 0 ; i < arr.length() ; i++) {
                                jsonObject = new JSONObject(arr.get(i).toString());
                                System.out.println(jsonObject.optString("tittle"));
                                setInitialData(jsonObject.optString("tittle"),
                                        jsonObject.optString("date"),
                                        jsonObject.optString("text"),
                                        jsonObject.optString("img"));


                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            // создаем адаптер
                            NewsStateAdapter adapter = new NewsStateAdapter(NewsActivity.this, states);
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