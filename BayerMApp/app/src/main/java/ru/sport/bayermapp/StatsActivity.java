package ru.sport.bayermapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.sport.bayermapp.StatsAdapter.StatsState;
import ru.sport.bayermapp.StatsAdapter.StatsStateAdapter;

public class StatsActivity extends AppCompatActivity {
    ArrayList<StatsState> states = new ArrayList<StatsState>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_stats);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(StatsActivity.this)
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
            Glide.with(StatsActivity.this)
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

    private void setInitialData(String st0, String st1,String st2,String st3,String st4,String st5,String st6) {

        states.add(new StatsState(st0,st1,st2,st3,st4,st5,st6));
    }




    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/BayerMApp/stat.json")
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
                            System.out.println(myResponse);
                            JSONObject jsonObject = new JSONObject(myResponse);
                            for (int i = 0 ; jsonObject.getJSONArray("stats").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("stats").get(i)));
                                setInitialData(
                                        json.optString("st0"),
                                        json.optString("st1"),
                                        json.optString("st2"),
                                        json.optString("st3"),
                                        json.optString("st4"),
                                        json.optString("st5"),
                                        json.optString("st6")
                                        );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = findViewById(R.id.list);

                        // создаем адаптер
                        StatsStateAdapter adapter = new StatsStateAdapter(StatsActivity.this, states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }


}