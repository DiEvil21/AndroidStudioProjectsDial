package ru.sport.spravochnik;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnalysActivity extends AppCompatActivity {
    TextView textV_title, textV_text;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analys);
        //http://159.69.90.204/api/spravochnik/analys.json
        textV_text = findViewById(R.id.textV_text);
        textV_title = findViewById(R.id.textV_title);


        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout linear = findViewById(R.id.linear_analys);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(AnalysActivity.this)
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
            Glide.with(AnalysActivity.this)
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






    void req() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/spravochnik/analys.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String respon = response.body().string();

                AnalysActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(respon);
                        JSONObject jsonFfdsfs = null;
                        try {
                            jsonFfdsfs = new JSONObject(respon);
                            jsonFfdsfs.optString("title");
                            textV_title.setText(jsonFfdsfs.optString("title"));
                            textV_text.setText(jsonFfdsfs.optString("text"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }

                });

            }
        });
    }


}