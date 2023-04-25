package ru.sport.anothersportapp;

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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
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

public class QuizeActivity extends AppCompatActivity {
    JSONArray questionsArr;
    TextView tv_question;
    Button btn_answer1,btn_answer2, btn_answer3, btn_answer4, btn_next;
    String correctAnswer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);
        btn_answer4 = findViewById(R.id.btn_answer4);
        btn_next = findViewById(R.id.btn_next);
        tv_question = findViewById(R.id.tv_question);


        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_quize);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(QuizeActivity.this)
                    .load("http://159.69.90.204/api/AnotherSportApp/background_horizontal.png")
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
            Glide.with(QuizeActivity.this)
                    .load("http://159.69.90.204/api/AnotherSportApp/background.png")
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



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuestion(questionsArr);
            }
        });
        try {
            getQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        btn_next.setVisibility(View.VISIBLE);
        Button btn = (Button) v;
        System.out.println(btn.getText());
        if (btn.getText().equals(correctAnswer)) {
            btn.setBackgroundColor(btn.getContext().getResources().getColor(R.color.green));
        }else {
            btn.setBackgroundColor(btn.getContext().getResources().getColor(R.color.red));
            findCorrectBtn();
        }
    }
    public void findCorrectBtn() {
        System.out.println(correctAnswer);
        if (btn_answer1.getText().equals(correctAnswer)) {
            btn_answer1.setBackgroundColor(btn_answer1.getContext().getResources().getColor(R.color.green));
        }else if (btn_answer2.getText().equals(correctAnswer)) {
            btn_answer2.setBackgroundColor(btn_answer2.getContext().getResources().getColor(R.color.green));
        }else if (btn_answer3.getText().equals(correctAnswer)) {
            btn_answer3.setBackgroundColor(btn_answer3.getContext().getResources().getColor(R.color.green));
        }else if (btn_answer4.getText().equals(correctAnswer)) {
            btn_answer4.setBackgroundColor(btn_answer4.getContext().getResources().getColor(R.color.green));
        }
    }


    public void setQuestion(JSONArray json) {
        btn_next.setVisibility(View.INVISIBLE);
        btn_answer1.setBackgroundColor(btn_answer1.getContext().getResources().getColor(R.color.blue));
        btn_answer2.setBackgroundColor(btn_answer2.getContext().getResources().getColor(R.color.blue));
        btn_answer3.setBackgroundColor(btn_answer3.getContext().getResources().getColor(R.color.blue));
        btn_answer4.setBackgroundColor(btn_answer4.getContext().getResources().getColor(R.color.blue));
        Random rnd = new Random();
        if (json != null) {
            int count = rnd.nextInt(json.length());
            try {
                JSONObject jsonObject = new JSONObject(json.get(count).toString());
                tv_question.setText(jsonObject.optString("question"));
                correctAnswer = jsonObject.optString("correctOption");
                JSONArray answersArr = new JSONArray(jsonObject.optString("options"));
                btn_answer1.setText(answersArr.get(0).toString());
                btn_answer2.setText(answersArr.get(1).toString());
                btn_answer3.setText(answersArr.get(2).toString());
                btn_answer4.setText(answersArr.get(3).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void getQuestions() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/AnotherSportApp/questions.json")
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
                            JSONObject allObject = new JSONObject(myResponse);
                            questionsArr = new JSONArray(allObject.optString("questions"));
                            setQuestion(questionsArr);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

            }
        });
    }


}