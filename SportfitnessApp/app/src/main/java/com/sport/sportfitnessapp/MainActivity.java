package com.sport.sportfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.main_back);
        String imageUrl = "http://159.69.90.204/api/SportfitnessApp/background.jpg";
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Button buttonExercises = findViewById(R.id.buttonExercises);
        Button buttonProgress = findViewById(R.id.buttonProgress);

        buttonExercises.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ExerciseListActivity.class));
        });

        buttonProgress.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProgressActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int savedRepetitions = prefs.getInt("repetitions", 0);
        // Обновите ваш ProgressBar в MainActivity, если есть
        ProgressBar progressBar = findViewById(R.id.progressBar);
        int maxRepetitions = 100; // Предположим, что максимальное количество подтягиваний - 100
        int progress = (savedRepetitions * 100) / maxRepetitions;
        progressBar.setProgress(progress);
    }
}

