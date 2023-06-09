package ru.sport.sportlistidk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        ConstraintLayout linearLayout = findViewById(R.id.load_back);
        String imageUrl = "http://159.69.90.204/api/SportListIdk/background.png";

        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        linearLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        // Обработка ошибки загрузки картинки
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // Подготовка к загрузке картинки (можно показать заглушку)
                    }
                });


    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent  = new Intent(LoadActivity.this,MenuActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 3000);
    }
}