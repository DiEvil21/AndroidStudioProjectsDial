package com.sport.sportfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoadActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ConstraintLayout background = findViewById(R.id.load_back);
        String imageUrl = "http://159.69.90.204/api/SportfitnessApp/background.jpg";
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        resetRepetitionsIfNecessary();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 миллисекунд (3 секунды)
    }

    private void resetRepetitionsIfNecessary() {
        int savedRepetitions = prefs.getInt("repetitions", 0);

        if (savedRepetitions > 0) {
            String lastResetDate = prefs.getString("last_reset_date", "");
            String currentDate = getCurrentDate();

            if (!currentDate.equals(lastResetDate)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("repetitions", 0); // Сбрасываем значение
                editor.putString("last_reset_date", currentDate); // Обновляем дату сброса
                editor.apply();
            }
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
