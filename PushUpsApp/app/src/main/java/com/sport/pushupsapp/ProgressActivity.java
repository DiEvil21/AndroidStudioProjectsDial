package com.sport.pushupsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> xps = new ArrayList<String>();
    EditText number;
    Spinner spinner;
    TextView tv_progress;
    String url;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        spinner = (Spinner) findViewById(R.id.planets_spinner);
        number = findViewById(R.id.et_num);
        tv_progress = findViewById(R.id.tv_progress);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setProgressBarValue(prefs.getInt("progress", 0));
        url = "http://159.69.90.204/api/PushUpsApp/info.json";
        LinearLayout linear = findViewById(R.id.linear_progress);
        Glide.with(ProgressActivity.this)
                .load("http://159.69.90.204/api/PushUpsApp/background.png")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linear.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void onCLick2(View v) {
        Intent intent = new Intent(ProgressActivity.this, InfoActivity.class);
        startActivity(intent);
    }
    public void onClick(View v) {
        String text = spinner.getSelectedItem().toString();
        int xp  = Integer.parseInt(xps.get((int) spinner.getSelectedItemId()));
        int num = Integer.parseInt(String.valueOf(number.getText()));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("progress", xp*num + prefs.getInt("progress", 0));
        editor.apply();
        tv_progress.setText(prefs.getInt("progress", 0) + " / 100 XP");
        setProgressBarValue(prefs.getInt("progress", 0));
    }

    public void setProgressBarValue(int progress) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(progress);
    }

    public void setSpinnerInfo() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //parent.getItemAtPosition(pos);
        //System.out.println("-- --- -- - -- - - - - --- - - " + parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



    void run() throws IOException {
        System.out.println("---------------------------------");
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
                System.out.println("----------------------");
                final String myResponse = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);
                        System.out.println("-------------------------------------------------");
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            System.out.println(jsonObject.optString("Header"));
                            JSONArray arr = new JSONArray(jsonObject.optString("list"));
                            for (int i = 0; i< arr.length(); i++) {
                                names.add(arr.getJSONObject(i).optString("вид"));
                                xps.add(arr.getJSONObject(i).optString("xp"));
                                setSpinnerInfo();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

            }
        });
    }

}