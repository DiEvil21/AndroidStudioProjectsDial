package ru.sport.truefalsevictorina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String response;
    Boolean answer;
    TextView tv_question, tv_anim;
    Animation anim;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_question = findViewById(R.id.tv_question);
        tv_anim = findViewById(R.id.tv_anim);
        anim = AnimationUtils.loadAnimation(this,R.anim.answeranim);
        //-----------------------------------------------------------------------------
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/TrueFalseVictorina/img/background_horizontal.png")
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
                    .load("http://159.69.90.204/api/TrueFalseVictorina/img/background.png")
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
        //-----------------------------------------------------------------------------
        try {
            getQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View vv) {
        switch (vv.getId()) {
            case R.id.btn_false:
                setToast(false);
                break;
            case R.id.btn_true:
                setToast(true);
                break;
        }
    }
    public void setToast(Boolean bool) {
        if (bool == answer) {
           /* Toast toast = Toast.makeText(getApplicationContext(),
                    "You are right!", Toast.LENGTH_SHORT);
            toast.show();*/
            tv_anim.setText("You are right!");
            tv_anim.startAnimation(anim);
        }else {
           /* Toast toast = Toast.makeText(getApplicationContext(),
                    "Wrong!Try again!", Toast.LENGTH_SHORT);
            toast.show();*/
            tv_anim.setText("Wrong!Try again!");
            tv_anim.startAnimation(anim);

        }
        resetQuestion(response);
    }

    public void resetQuestion(String response) {
        this.response = response;
        try {
            JSONObject jsonOb = new JSONObject(response);
            JSONArray arr = new JSONArray(jsonOb.getJSONArray("questions").toString());
            Random rand = new Random();

            int n = rand.nextInt(arr.length());
            JSONObject json = new JSONObject(arr.get(n).toString());
            tv_question.setText(json.optString("question"));
            System.out.println(json.opt("answer"));
            answer = (Boolean) json.opt("answer");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void getQuestions() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/TrueFalseVictorina/questions.json")
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
                        resetQuestion(myResponse);




                    }
                });

            }
        });
    }


}