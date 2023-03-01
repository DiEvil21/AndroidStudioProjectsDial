package ru.sport.historyfootball;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import ru.sport.historyfootball.menu.menuState;
import ru.sport.historyfootball.menu.menuStateAdapter;

public class MenuActivity extends AppCompatActivity {
    ArrayList<menuState> states = new ArrayList<menuState>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_menu);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MenuActivity.this)
                    .load("http://159.69.90.204/api/FootballHistory/img/pole_horizontal.png")
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
            Glide.with(MenuActivity.this)
                    .load("http://159.69.90.204/api/FootballHistory/img/pole.png")
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
            nud();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialData(String header, String img, String img2, String text){

        states.add(new menuState (header,img,img2,text));


    }

    void nud() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/FootballHistory/menu.json")
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
                            JSONArray arr = new JSONArray(myResponse);
                            for (int i = 0; arr.length() > i ; i++) {
                                arr.get(i);
                                JSONObject json = new JSONObject(arr.get(i).toString());
                                System.out.println(json);
                                System.out.println(json.optString("img"));
                                initialData(json.optString("header"),
                                        json.optString("img"),
                                        json.optString("img2"),
                                        json.optString("text"));
                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                            // создаем адаптер
                            menuStateAdapter adapter = new menuStateAdapter(MenuActivity.this, states);
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