package com.sport.noobsfitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linear = findViewById(R.id.linear_main);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/NoobsFitnessApp/img/background_main.png")
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


    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_rec:
                intent = new Intent(MainActivity.this,RecommendedActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_prog:
                intent = new Intent(MainActivity.this,SelectActivity.class);
                startActivity(intent);
        }
    }
}