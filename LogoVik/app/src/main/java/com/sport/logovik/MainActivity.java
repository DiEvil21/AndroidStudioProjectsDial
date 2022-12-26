package com.sport.logovik;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;


import android.app.Activity;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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


public class MainActivity extends Activity {
    String jsonStr = "";
    final Random random = new Random();
    ImageView iv_logo;
    Button btn_check, btn_hint;
    String trueAnswer,trueImg;
    EditText et_answer;
    RestartDialog restartDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawingView mDrawingView = (DrawingView) findViewById(R.id.drawing);
        mDrawingView.setErase(true);
        mDrawingView.setBrushSize(60);
        iv_logo = findViewById(R.id.iv_logo);
        btn_check = findViewById(R.id.btn_check);
        et_answer = findViewById(R.id.et_answer);
        btn_hint = findViewById(R.id.btn_hint);
        restartDialog = new RestartDialog();
        LinearLayout linear = findViewById(R.id.linear_main);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/logoVik/background.png")
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linear.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        btn_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_answer.setText(trueAnswer);
            }
        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_answer.getText().toString().equals("")) {
                    if (et_answer.getText().toString().equals(trueAnswer)) {
                        et_answer.getText().clear();
                        mDrawingView.restart();
                        try {
                            run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        restartDialog.showDialog(MainActivity.this, trueAnswer, trueImg);
                    }else {
                        Toast.makeText(getApplicationContext(),R.string.toast_error, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast toast= Toast.makeText(getApplicationContext(),R.string.toast_null, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/logoVik/questions/questions.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);
                        jsonStr = myResponse;
                        JSONArray jsonArr = null;
                        try {
                            jsonArr = new JSONArray(jsonStr);
                            JSONObject jsonObject = new JSONObject(jsonArr.get(random.nextInt(jsonArr.length())).toString());
                            System.out.println(jsonObject.optString("answer"));
                            trueAnswer = jsonObject.optString("answer");
                            trueImg = jsonObject.optString("img");
                            //jsonObject.optString("img")
                            Glide.with(getApplicationContext())
                                    .load(jsonObject.optString("img"))
                                    .override(300, 200)
                                    .into(iv_logo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                });

            }
        });
    }
}