package ru.sport.footballstrateg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logo_menu = findViewById(R.id.iv_menu);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/FootballStrateg/img/logo.png")
                .into(logo_menu);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/FootballStrateg/img/pole_horizontal.png")
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
                    .load("http://159.69.90.204/api/FootballStrateg/img/pole.png")
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
    }


    public void onCLick(View v) {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        switch (v.getId()) {
            case R.id.tactic_btn:
                intent.putExtra("url", "http://159.69.90.204/api/FootballStrateg/tactics.json");
                break;
            case R.id.class_btn:
                intent.putExtra("url", "http://159.69.90.204/api/FootballStrateg/class.json");
                break;
            case R.id.train_btn:
                intent.putExtra("url", "http://159.69.90.204/api/FootballStrateg/train.json");
                break;

        }
        startActivity(intent);

    }



}