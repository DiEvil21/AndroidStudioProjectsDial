package ru.sport.newsligas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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
import ru.sport.newsligas.ui.Info;
import ru.sport.newsligas.ui.InfoAdapter;

public class MainActivity extends AppCompatActivity {
    ProgressBar bar;
    ArrayList<Info> infoList = new ArrayList<Info>();
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.progress_bar);
        url = "http://159.69.90.204/api/NewsLigas/champion.json";
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/newsOfFootball/img/background_horizontal.png")
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
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/newsOfFootball/img/background.png")
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
            send_dudes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) throws IOException {
        switch (v.getId()) {
            case R.id.button:
                url = "http://159.69.90.204/api/NewsLigas/champion.json";
                break;
            case R.id.button2:
                url = "http://159.69.90.204/api/NewsLigas/european.json";
                break;
            case R.id.button3:
                url = "http://159.69.90.204/api/NewsLigas/la.json";
                break;
        }
        send_dudes();
    }
    public void setInfo(String title, String date, Spanned text, String image){

        infoList.add(new Info (title,date,text,image));


    }


    void send_dudes() throws IOException {
        bar.setVisibility(View.VISIBLE);
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
                        bar.setVisibility(View.GONE);
                        String str = "";
                        Spanned wall = Html.fromHtml("");
                        try {
                            infoList = new ArrayList<Info>();
                            JSONObject jsonObject = new JSONObject(myResponse);
                            System.out.println(myResponse);
                            JSONArray arr = new JSONArray(jsonObject.optString("arr"));
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject json = new JSONObject(arr.get(i).toString());
                                JSONArray jArr = new JSONArray(json.optString("news"));
                                for (int n = 0; n < jArr.length(); n++) {
                                    JSONObject jsonObject1  = new JSONObject(jArr.get(n).toString());
                                    str = str + "<font color=#9e9e9e>" + jsonObject1.optString("time") + "</font> "+ jsonObject1.optString("text") +"<br><br>";
                                    if (jsonObject1.optString("text").equals("")) {
                                        str = "";
                                    }

                                }
                                wall = Html.fromHtml(str);
                                setInfo(json.optString("header"),json.optString("date"),wall,json.optString("img"));
                                str = "";
                            }



                            RecyclerView recyclerView = findViewById(R.id.list);
                            // создаем адаптер
                            InfoAdapter adapter = new InfoAdapter(MainActivity.this, infoList);
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