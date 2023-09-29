package ru.sport.rouletteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import coil.ImageLoader;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ConstraintLayout background = findViewById(R.id.load_constraint);
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



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 миллисекунд (3 секунды)
    }
}