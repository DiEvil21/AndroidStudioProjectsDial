package ru.sport.spravochnik;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
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

public class InfoforecastActivity extends AppCompatActivity {
    ArrayList<InfoForecast> info = new ArrayList<InfoForecast>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoforecast);


        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout linear = findViewById(R.id.linear_forecast);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(InfoforecastActivity.this)
                    .load("http://159.69.90.204/api/spravochnik/img/pole_horizontal.png")
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
            Glide.with(InfoforecastActivity.this)
                    .load("http://159.69.90.204/api/spravochnik/img/pole.png")
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




        try {
            req();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setInfo(String header, String about){

        info.add(new InfoForecast (header, about));


    }






    void req() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/spravochnik/forecast.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String respon = response.body().string();

                InfoforecastActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray jsonFfdsfs = null;
                        try {
                            jsonFfdsfs = new JSONArray(respon);
                            for (int i = 0 ; i < jsonFfdsfs.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonFfdsfs.get(i).toString());
                                System.out.println(jsonObject.optString("name"));
                                setInfo(jsonObject.optString("name"),jsonObject.optString("about"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RecyclerView recyclerView = findViewById(R.id.list);
                        // создаем адаптер
                        InfoForecastAdapter adapter = new InfoForecastAdapter(InfoforecastActivity.this, info);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);






                    }

                });

            }
        });
    }



}