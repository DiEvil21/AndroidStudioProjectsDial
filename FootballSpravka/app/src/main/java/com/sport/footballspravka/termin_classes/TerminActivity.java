package com.sport.footballspravka.termin_classes;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sport.footballspravka.MainActivity;
import com.sport.footballspravka.R;

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

public class TerminActivity extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();
    String url;
    LinearLayout linear;
    JSONObject jsonObject;
    TextView textHeader;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin);
        url = "http://159.69.90.204/api/footballSpravka/termins.json";
        textHeader = findViewById(R.id.textViewHeader);
        linear = (LinearLayout) findViewById(R.id.linear_termin);
        Glide.with(TerminActivity.this)
                .load("http://159.69.90.204/api/footballSpravka/background.jpg")
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
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(getApplicationContext(), states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    public void setInitialData(String header, String text){

        states.add(new State (header,text));


    }



    void run() throws IOException {
        System.out.println("---------------------------------");
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
                System.out.println("----------------------");
                final String myResponse = response.body().string();

                TerminActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);
                        try {
                            jsonObject = new JSONObject(myResponse);
                            textHeader.setText(jsonObject.optString("Header"));
                            //System.out.println(jsonObject.optString("noobs"));
                            JSONArray arr = new JSONArray(jsonObject.optString("questions"));
                            for (int i = 0; arr.length() > i ; i++) {
                                System.out.println(arr.getJSONObject(i).optString("question"));
                                setInitialData(arr.getJSONObject(i).optString("question"),
                                        arr.getJSONObject(i).optString("answer"));
                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            // создаем адаптер
                            StateAdapter adapter = new StateAdapter(TerminActivity.this, states);
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