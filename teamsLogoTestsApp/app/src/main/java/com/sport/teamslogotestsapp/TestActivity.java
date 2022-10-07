package com.sport.teamslogotestsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
    ImageView img, imgWall;
    Button btn1, btn2, btn3, btn4;
    TextView textViewDialog;
    JSONArray json;
    String jsonStr = "";
    RestartDialog restartDialog;
    Boolean chekerData = false;
    String trueAnswer = "";
    final Random random = new Random();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        restartDialog = new RestartDialog();

        img = findViewById(R.id.image);
        imgWall = findViewById(R.id.letter);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        img.setImageResource(R.drawable.background_logo);
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

    public void setBtnText(String trueAnswer, String a, String b,String c) {
        int i = random.nextInt(4);
        switch (i) {
            case 0:
                btn1.setText(trueAnswer);
                btn2.setText(a);
                btn3.setText(b);
                btn4.setText(c);
                break;
            case 1:
                btn1.setText(a);
                btn2.setText(trueAnswer);
                btn3.setText(b);
                btn4.setText(c);
                break;
            case 2:
                btn1.setText(b);
                btn2.setText(a);
                btn3.setText(trueAnswer);
                btn4.setText(c);
                break;
            case 3:
                btn1.setText(b);
                btn2.setText(a);
                btn3.setText(c);
                btn4.setText(trueAnswer);
                break;

        }
    }

    public void resetData() {
        if (chekerData) {
            try {

                JSONArray jsonArr = new JSONArray(jsonStr);
                JSONObject jsonObject = new JSONObject(jsonArr.get(random.nextInt(jsonArr.length())).toString());
                System.out.println(jsonObject);
                new DownloadImageTask(img).execute(jsonObject.optString("img"));
                trueAnswer = jsonObject.optString("answer");
                jsonObject = new JSONObject(jsonArr.get(random.nextInt(jsonArr.length())).toString());
                String a =  jsonObject.optString("answer");
                jsonObject = new JSONObject(jsonArr.get(random.nextInt(jsonArr.length())).toString());
                String b =  jsonObject.optString("answer");
                jsonObject = new JSONObject(jsonArr.get(random.nextInt(jsonArr.length())).toString());
                String c =  jsonObject.optString("answer");
                setBtnText(trueAnswer,a,b,c);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            int resId = getResources().getIdentifier("wall" + random.nextInt(10) , "drawable", getPackageName());;
            imgWall.setImageResource(resId);



        }


    }

    public void onClick(View v) {

        img.setImageResource(R.drawable.background_logo);

        switch (v.getId()) {
            case R.id.button1:
                if (btn1.getText().equals(trueAnswer)) {
                    System.out.println("верно");
                }else {
                    restartDialog.show(getSupportFragmentManager(),"custom");
                }

                break;
            case R.id.button2:
                if (btn2.getText().equals(trueAnswer)) {
                    System.out.println("верно");
                }else {
                    restartDialog.show(getSupportFragmentManager(),"custom");
                }


                break;
            case R.id.button3:
                if (btn3.getText().equals(trueAnswer)) {
                    System.out.println("верно");
                }else {
                    restartDialog.show(getSupportFragmentManager(),"custom");
                }


                break;
            case R.id.button4:
                if (btn4.getText().equals(trueAnswer)) {
                    System.out.println("верно");
                }else {
                    restartDialog.show(getSupportFragmentManager(),"custom");
                }


                break;
            case R.id.restartButtonDialog:
                restartDialog.dismiss();
        }
        resetData();
    }



    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/logoTestsApp/questions/questions.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                TestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);
                        jsonStr = myResponse;
                        chekerData = true;
                        resetData();




                    }
                });

            }
        });
    }
}