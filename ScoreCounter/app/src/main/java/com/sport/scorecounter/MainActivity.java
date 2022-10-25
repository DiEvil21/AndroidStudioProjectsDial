package com.sport.scorecounter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.linear);
        //new DownloadImageTask(linearLayout).execute("http://159.69.90.204/api/Scorecounter/background.jpg");
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/Scorecounter/background.jpg")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }


    public void onClick(View v ) {
        switch (v.getId()) {
            case R.id.button8:
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
                break;
            case R.id.button9:
                Intent intent1 = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent1);
                break;
        }
    }
}