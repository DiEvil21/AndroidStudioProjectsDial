package ru.sport.ballquize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionActivity extends AppCompatActivity {
    JSONArray arr;
    TextView tv_question;
    String currect_answer;
    Button btn_answer1, btn_answer2, btn_answer3, btn_answer4, btn_next;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        tv_question = findViewById(R.id.tv_question);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);
        btn_answer4 = findViewById(R.id.btn_answer4);
        btn_next = findViewById(R.id.btn_next);
        Intent intent = getIntent();



        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_question);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(QuestionActivity.this)
                    .load("http://159.69.90.204/api/ballQuize/background_horizontal.png")
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
            Glide.with(QuestionActivity.this)
                    .load("http://159.69.90.204/api/ballQuize/background.png")
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



        String url = intent.getStringExtra("url");
        try {
            getQuestions(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextQuestion() {
        btn_answer1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        btn_answer2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        btn_answer3.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        btn_answer4.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(arr.get(i).toString());
            tv_question.setText(jsonObject.optString("question"));
            currect_answer = jsonObject.optString("answer");
            JSONArray jsonArray = new JSONArray(jsonObject.optString("options"));
            btn_answer1.setText(jsonArray.get(0).toString());
            btn_answer2.setText(jsonArray.get(1).toString());
            btn_answer3.setText(jsonArray.get(2).toString());

            btn_answer4.setText(jsonArray.get(3).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (arr.length() > i++) {
            i++;
        }else {
            Intent intent = new Intent(QuestionActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }

    public void onClickNext(View v) {
        nextQuestion();
    }
    @SuppressLint("NonConstantResourceId")
    public void onCLick(View v) {
        btn_next.setVisibility(View.VISIBLE);
        Button b  = (Button) v;
        b.setBackgroundColor(setColor(currect_answer, String.valueOf(b.getText())));

        if (Objects.equals(currect_answer, (String) btn_answer1.getText())) {
            btn_answer1.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        }else if (Objects.equals(currect_answer, (String) btn_answer2.getText())) {
            btn_answer2.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        }else if (Objects.equals(currect_answer, (String) btn_answer3.getText())) {
            btn_answer3.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        }else if (Objects.equals(currect_answer, (String) btn_answer4.getText())) {
            btn_answer4.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        }
    }
    private int setColor(String str1, String str2) {

        if (!Objects.equals(str1, str2)) {
            return R.color.red;
        }else return R.color.green;
    }


    void getQuestions(String url) throws IOException {

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
                        arr = new JSONArray(myResponse);
                        nextQuestion();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }







                    }

                });

            }
        });
    }



}