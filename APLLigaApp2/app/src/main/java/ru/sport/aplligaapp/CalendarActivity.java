package ru.sport.aplligaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
import ru.sport.aplligaapp.states.Calendar;
import ru.sport.aplligaapp.states.CalendarAdapter;
import ru.sport.aplligaapp.states.News;
import ru.sport.aplligaapp.states.NewsAdapter;

public class CalendarActivity extends AppCompatActivity {
    ArrayList<Calendar> states = new ArrayList<Calendar>();
    ProgressBar bar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        bar = findViewById(R.id.progress_bar);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_calendar);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(CalendarActivity.this)
                    .load("http://159.69.90.204/api/APLLiga/img/pole_horizontal.png")
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
            Glide.with(CalendarActivity.this)
                    .load("http://159.69.90.204/api/APLLiga/img/pole.png")
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
            getNude();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setData(String date, String owner, String score, String quest) {

        states.add(new Calendar(date,owner,score,quest));
    }


    void getNude() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/APLLiga/calendar.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("----------------------");
                final String myResponse = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //System.out.println(myResponse);
                            JSONArray arr = new JSONArray(myResponse);
                            //System.out.println(arr.get(0).toString());
                            JSONObject jsonObject1 = new JSONObject(arr.getJSONObject(0).toString());
                            System.out.println(jsonObject1);
                            JSONArray array = new JSONArray(jsonObject1.optString("stats"));
                            for (int i = 0 ; i < array.length(); i++) {
                                JSONObject jsonObj  =  array.getJSONObject(i);
                                System.out.println(jsonObj.optString("date"));
                                setData(jsonObj.optString("date"),
                                        jsonObj.optString("owner"),
                                        jsonObj.optString("score"),
                                        jsonObj.optString("quests"));
                            }
                            bar.setVisibility(View.GONE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = findViewById(R.id.list);
                        recyclerView.setNestedScrollingEnabled(false);

                        // создаем адаптер
                        CalendarAdapter adapter = new CalendarAdapter(CalendarActivity.this, states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);
                        //-------------------------------------------------------------------




                    }

                });

            }
        });
    }


}