package ru.sport.idkapp;

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
    ImageView img_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_logo = findViewById(R.id.imageView);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/idkApp/background_horizontal.png")
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
                    .load("http://159.69.90.204/api/idkApp/background.png")
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
        Glide.with(img_logo.getContext())
                .load("http://159.69.90.204/api/idkApp/logo.png")
                .into(img_logo);
    }


    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,ContentActivity.class);
        switch (v.getId()) {
            case R.id.button:
                intent.putExtra("url","http://159.69.90.204/api/idkApp/rules.json");
                break;
            case R.id.button2:
                intent.putExtra("url","http://159.69.90.204/api/idkApp/termins.json");
                break;
        }
        startActivity(intent);
    }
}