package ru.sport.idkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
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

public class ContentActivity extends AppCompatActivity {
    String url;
    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        textView = findViewById(R.id.textView_text);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_content);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(ContentActivity.this)
                    .load("http://159.69.90.204/api/idkApp/background_horizontal.png")
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
            Glide.with(ContentActivity.this)
                    .load("http://159.69.90.204/api/idkApp/background.png")
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
            getJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    void getJson() throws IOException {

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
                        String str ="";
                        try {
                            JSONArray arr = new JSONArray(myResponse);
                            for (int  i = 0;  i < arr.length(); i++) {
                                JSONObject jsonObj = new JSONObject(arr.get(i).toString());
                                str += "<div style=\"text-align: center\">" + "<h2>"+jsonObj.optString("word") +"</h2>" + "</div>"  +  jsonObj.optString("meaning") ;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textView.setText( Html.fromHtml (str));


                    }
                });

            }
        });
    }



}