package ru.sport.sportlistidk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MenuActivity extends AppCompatActivity {
    Button btn_facts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btn_facts = findViewById(R.id.button3);
        LinearLayout linearLayout = findViewById(R.id.menu_back);
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

        btn_facts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}