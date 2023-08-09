package com.sport.ppfitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class LoadActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
        ConstraintLayout background = findViewById(R.id.load_back);
        String imageUrl = "http://159.69.90.204/api/PPfitnessApp/background.jpg";
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String workoutsJson = sharedPreferences.getString("workouts", "");
                if (workoutsJson.isEmpty()) {
                    Intent intent = new Intent(LoadActivity.this, StartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000); // 3000 миллисекунд (3 секунды)
    }
}