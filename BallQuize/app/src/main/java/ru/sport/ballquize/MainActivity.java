package ru.sport.ballquize;

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
    String url;
    ImageView img1, img2, img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = findViewById(R.id.iv_img1);
        img2 = findViewById(R.id.iv_img2);
        img3 = findViewById(R.id.iv_img3);

        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/ballQuize/background_horizontal.png")
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
                    .load("http://159.69.90.204/api/ballQuize/background.png")
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





        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/ballQuize/img1.png")
                .into(img1);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/ballQuize/img2.png")
                .into(img2);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/ballQuize/img3.png")
                .into(img3);
    }

    public void onCLick(View v) {
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        switch (v.getId()) {
            case R.id.menu1:
                url = "http://159.69.90.204/api/ballQuize/menu1.json";
                break;
            case R.id.menu2:
                url = "http://159.69.90.204/api/ballQuize/menu2.json";
                break;
            case R.id.menu3:
                url = "http://159.69.90.204/api/ballQuize/menu3.json";
                break;
        }
        intent.putExtra("url", url);
        startActivity(intent);
    }
}