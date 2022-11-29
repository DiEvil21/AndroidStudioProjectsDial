package com.sport.footballspravka.rules_classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

public class RulesActivity extends AppCompatActivity {
    String url;
    LinearLayout linear;
    JSONObject jsonObject;
    TextView textHeader;
    ArrayList<RulesState> states = new ArrayList<RulesState>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Bundle arguments = getIntent().getExtras();
        url = arguments.get("url").toString();
        textHeader = findViewById(R.id.textViewHeader);
        textHeader.setText(url);
        linear = (LinearLayout) findViewById(R.id.linear_rules);
        Glide.with(RulesActivity.this)
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


    }
    public void setInitialData(String header, String text, String img){

        states.add(new RulesState (header,text,img));


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

                RulesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);
                        try {
                            jsonObject = new JSONObject(myResponse);
                            textHeader.setText(jsonObject.optString("Header"));
                            System.out.println(jsonObject.optString("noobs"));
                            JSONArray arr = new JSONArray(jsonObject.optString("noobs"));
                            for (int i = 0; arr.length() > i ; i++) {
                                System.out.println(arr.getJSONObject(i).optString("question"));
                                setInitialData(arr.getJSONObject(i).optString("question"),
                                        arr.getJSONObject(i).optString("answer"),
                                        arr.getJSONObject(i).optString("img"));
                            }
                            RecyclerView recyclerView = findViewById(R.id.list);
                            // создаем адаптер
                            RulesStateAdapter adapter = new RulesStateAdapter(RulesActivity.this, states);
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