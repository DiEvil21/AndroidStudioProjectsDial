package ru.sport.onemoresportapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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

public class SecondActivity extends AppCompatActivity {
    JSONArray arr;
    TextView tv_question;
    EditText et_answer;
    Button btn_check;
    String correctAnswer;
    Animation animation;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        et_answer = findViewById(R.id.et_answer);
        tv_question = findViewById(R.id.tv_question);
        btn_check = findViewById(R.id.btn_check);

        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_second);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(SecondActivity.this)
                    .load("http://159.69.90.204/api/OneMoreSportApp/background_horizontal.png")
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
            Glide.with(SecondActivity.this)
                    .load("http://159.69.90.204/api/OneMoreSportApp/background.png")
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



        animation = AnimationUtils.loadAnimation(this, R.anim.answer_anim);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    public void setQuestion(JSONArray array) {
        Random rnd = new Random();
        if (array != null) {
            int count = rnd.nextInt(array.length());
            try {
                JSONObject jsonObject = new JSONObject(array.get(count).toString());
                tv_question.setText(jsonObject.optString("question"));
                correctAnswer = jsonObject.optString("answer");
                System.out.println(correctAnswer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void checkAnswer() {
        String str = String.valueOf(et_answer.getText());
        if (str.toUpperCase().equals(correctAnswer.toUpperCase())) {
            showDialog();
            setQuestion(arr);

        }else {
            et_answer.startAnimation(animation);
        }
    }
    public void showDialog() {
        et_answer.setText("");
        AnswerFragment dialog = new AnswerFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }


    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/OneMoreSportApp/quize.json")
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
                            setQuestion(arr);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

            }
        });
    }


}