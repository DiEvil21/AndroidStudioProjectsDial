package ru.sport.footballquise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
    String response, question;
    Boolean answer;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.textView2);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout linear = findViewById(R.id.linear_test);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(TestActivity.this)
                    .load("http://159.69.90.204/api/FootballQuise/img/pole_horizontal.png")
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
            Glide.with(TestActivity.this)
                    .load("http://159.69.90.204/api/FootballQuise/img/pole.png")
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



    }

    public void onClick(View vv) {
        switch (vv.getId()) {
            case R.id.but_false:
                checkAnswer(false);
                break;
            case R.id.but_true:
                checkAnswer(true);
                break;
        }
    }
    public void checkAnswer(Boolean bool) {
        if (bool == answer) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You are right!", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Wrong!Try again!", Toast.LENGTH_SHORT);
            toast.show();
        }
        setQuestion(response);
    }

    public void setQuestion(String response) {
        this.response = response;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray arr = new JSONArray(jsonObject.getJSONArray("questions").toString());
            Random rand = new Random();

            int n = rand.nextInt(arr.length());
            JSONObject json = new JSONObject(arr.get(n).toString());
            textView.setText(json.optString("question"));
            System.out.println(json.opt("answer"));
            answer = (Boolean) json.opt("answer");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/FootballQuise/tests.json")
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
                        setQuestion(myResponse);





                    }

                });

            }
        });
    }
}