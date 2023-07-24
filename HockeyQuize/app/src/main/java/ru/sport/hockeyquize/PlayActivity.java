package ru.sport.hockeyquize;

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
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Button btn_play = findViewById(R.id.button);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.play_back);
        String imageUrl = "http://159.69.90.204/api/HockeyQuize/background.png"; // Замените ссылку на вашу URL-ссылку на изображение
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
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}