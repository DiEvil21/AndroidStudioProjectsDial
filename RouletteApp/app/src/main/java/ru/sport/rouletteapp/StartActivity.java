package ru.sport.rouletteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class StartActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.start_lenear);
        String imageUrl = "http://159.69.90.204/api/RouletteApp/background.jpg"; // Замените ссылку на вашу URL-ссылку на изображение
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                // Обработка ошибки загрузки изображения
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Действия при подготовке загрузки изображения
            }
        });
        ImageView logo = findViewById(R.id.imageView);
        Picasso.get().load("http://159.69.90.204/api/RouletteApp/menu_logo.png").into(logo);
        Button btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}