package com.sport.footballspravka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sport.footballspravka.rules_classes.RulesActivity;
import com.sport.footballspravka.termin_classes.TerminActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = (LinearLayout) findViewById(R.id.linear_main);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/footballSpravka/background.jpg")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linear.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void onCLick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_termins:
                intent = new Intent(MainActivity.this, TerminActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_rules:
                intent = new Intent(MainActivity.this, RulesActivity.class);
                intent.putExtra("url","http://159.69.90.204/api/footballSpravka/rules.json");
                startActivity(intent);
                break;
            case R.id.btn_struct:
                intent = new Intent(MainActivity.this, RulesActivity.class);
                intent.putExtra("url","http://159.69.90.204/api/footballSpravka/rules2.json");
                startActivity(intent);

        }
    }
}