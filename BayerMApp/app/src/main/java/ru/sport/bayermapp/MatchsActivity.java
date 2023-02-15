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
import ru.sport.bayermapp.MatchAdapter.MatchState;
import ru.sport.bayermapp.MatchAdapter.MatchStateAdapter;

public class MatchsActivity extends AppCompatActivity {
    ArrayList<MatchState> states = new ArrayList<MatchState>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchs);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_matchs);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MatchsActivity.this)
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
            Glide.with(MatchsActivity.this)
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

    private void setInitialData(String name, String role, String country, String img) {

        states.add(new MatchState(name,role,country,img));
    }



    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/BayerMApp/matchs.json")
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
                        System.out.println("-------------------------------------------------");
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            System.out.println(jsonObject.getJSONArray("players").length());
                            for (int i = 0 ; jsonObject.getJSONArray("players").length() > i; i++){
                                System.out.println("--------------");
                                System.out.println(jsonObject.getJSONArray("players").get(i));
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("players").get(i)));
                                setInitialData(json.optString("name"),
                                        json.optString("role"),
                                        json.optString("date"),
                                        json.optString("img"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = findViewById(R.id.list);

                        // создаем адаптер
                        MatchStateAdapter adapter = new MatchStateAdapter(MatchsActivity.this, states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }





}