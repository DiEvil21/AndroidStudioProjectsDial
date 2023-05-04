package ru.sport.morequize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
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
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QiuzeActivity extends AppCompatActivity {
    JSONArray arr;
    JSONObject questionObj;
    TextView tv_question,tv_score;
    Button btn_true, btn_false;
    Boolean answer;
    int score;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiuze);

        int orientation = getResources().getConfiguration().orientation;
        LinearLayout linear = findViewById(R.id.linear_question);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(QiuzeActivity.this)
                    .load("http://159.69.90.204/api/MoreQiuze/background_horizontal.png")
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
            Glide.with(QiuzeActivity.this)
                    .load("http://159.69.90.204/api/MoreQiuze/background.png")
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



        tv_question =(TextView) findViewById(R.id.tv_question);
        btn_false = findViewById(R.id.btn_false);
        btn_true = findViewById(R.id.btn_true);
        tv_score = findViewById(R.id.tv_score);
        score = 0;
        try {
            getQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestion();
                if (answer) {
                    CorrectDialog correctDialog =new CorrectDialog();
                    score++;
                    tv_score.setText("Score: " + score);
                    correctDialog.show(getSupportFragmentManager(),"tag2");
                }else {
                    FalseDialog falseDialog = new FalseDialog();
                    falseDialog.show(getSupportFragmentManager(),"tag");
                }
            }
        });
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestion();
                if (!answer) {
                    score ++;
                    tv_score.setText("Score: " + score);
                    CorrectDialog correctDialog =new CorrectDialog();
                    correctDialog.show(getSupportFragmentManager(),"tag2");
                }else {
                    FalseDialog falseDialog = new FalseDialog();
                    falseDialog.show(getSupportFragmentManager(),"tag");
                }
            }
        });
    }


    public void newQuestion() {
        Random rand = new Random();
        try {
            questionObj = new JSONObject(arr.get(rand.nextInt(arr.length())).toString());
            tv_question.setText(questionObj.optString("question"));
            answer = questionObj.optBoolean("answer");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    void getQuestion() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/MoreQiuze/questions.json")
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
                            newQuestion();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });
    }
}