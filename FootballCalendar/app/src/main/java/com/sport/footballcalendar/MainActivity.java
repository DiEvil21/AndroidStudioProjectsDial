package com.sport.footballcalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

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

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    String url, month;
    String[][] menu;
    ArrayList<String> names = new ArrayList<String>();
    ProgressBar mProgressBar;
    Spinner spinner;
    Button btn_prev, btn_now, btn_next;
    ArrayList<State> states = new ArrayList<State>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //------------------------------------------------------------------------------------------
        btn_next = findViewById(R.id.btn_next);
        btn_now = findViewById(R.id.btn_now);
        btn_prev = findViewById(R.id.btn_prev);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        spinner = (Spinner) findViewById(R.id.spinner);

        //------------------------------------------------------------------------------------------
        spinner.setOnItemSelectedListener(this);
        month = "stats_now";
        url = "http://159.69.90.204/api/footballCalendar/calendar.json";
        menu = new String[2][2];
        menu[0][0] = "Лига чемпионов УЕФА";
        menu[1][0] = "http://159.69.90.204/api/footballCalendar/calendar.json";
        menu[0][1] = "Лига Европы УЕФА";
        menu[1][1] = "http://159.69.90.204/api/footballCalendar/europe.json";
        //menu[0][2] = "лига 3";
        //menu[1][2] = "http://159.69.90.204/api/footballCalendar/europe.json";
        for (int i = 0; i < menu[0].length; i ++) {
            names.add(menu[0][i]);
        }
        setSpinnerInfo(names);
        //------------------------------------------------------------------------------------------



        //-----request------------------------------------------------------------------------------
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------------------

    }
    //------------fill State arrayList--------------------------------------------------------------
    private void setInitialData(String team1, String team2, String img2, String date, String img1, String score) {

        states.add(new State(team1,team2,img2,date,img1,score));
    }


    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    public void onClick(View v) {
        states = new ArrayList<State>();
        RecyclerView recyclerView = findViewById(R.id.list);
        // create adapter
        StateAdapter adapter = new StateAdapter(MainActivity.this, states);
        // install adapter for list
        recyclerView.setAdapter(adapter);
        switch (v.getId()) {
            case R.id.btn_now:
                btn_now.setBackground(getDrawable(R.drawable.active_button));
                btn_next.setBackground(getDrawable(R.drawable.btn_background));
                btn_prev.setBackground(getDrawable(R.drawable.btn_background));
                mProgressBar.setVisibility(View.VISIBLE);
                month = "stats_now";
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_next:
                btn_next.setBackground(getDrawable(R.drawable.active_button));
                btn_now.setBackground(getDrawable(R.drawable.btn_background));
                btn_prev.setBackground(getDrawable(R.drawable.btn_background));
                mProgressBar.setVisibility(View.VISIBLE);
                month = "stats_next";
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_prev:
                btn_prev.setBackground(getDrawable(R.drawable.active_button));
                btn_next.setBackground(getDrawable(R.drawable.btn_background));
                btn_now.setBackground(getDrawable(R.drawable.btn_background));
                mProgressBar.setVisibility(View.VISIBLE);
                month = "stats_prev";
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayout linear = findViewById(R.id.linear_main);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/footballCalendar/background.png")
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

    void run() throws IOException {

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
                            states = new ArrayList<State>();
                            JSONObject jsonObject = new JSONObject(myResponse);
                            System.out.println(jsonObject.optString("Header"));
                            btn_next.setText(jsonObject.optString("next"));
                            btn_now.setText(jsonObject.optString("now"));
                            btn_prev.setText(jsonObject.optString("prev"));
                            JSONArray arr = new JSONArray(jsonObject.optString(month));
                            //fill arraylist
                            for (int i = 0; i< arr.length(); i++) {
                                setInitialData(
                                        arr.getJSONObject(i).optString("owner"),
                                        arr.getJSONObject(i).optString("quests"),
                                        "",
                                        arr.getJSONObject(i).optString("name"),
                                        "",
                                        arr.getJSONObject(i).optString("score"));

                            }
                            //по кнопкам менять month, в json несколько stats_now, stats_prev...
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RecyclerView recyclerView = findViewById(R.id.list);
                        // create adapter
                        StateAdapter adapter = new StateAdapter(MainActivity.this, states);
                        // install adapter for list
                        recyclerView.setAdapter(adapter);
                        mProgressBar.setVisibility(View.GONE);





                    }
                });

            }
        });
    }
    public void setSpinnerInfo(ArrayList<String> names) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item,
                names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        states = new ArrayList<State>();
        RecyclerView recyclerView = findViewById(R.id.list);
        // create adapter
        StateAdapter adapter = new StateAdapter(MainActivity.this, states);
        // install adapter for list
        recyclerView.setAdapter(adapter);
        url = menu[1][(int) spinner.getSelectedItemId()];
        mProgressBar.setVisibility(View.VISIBLE);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}