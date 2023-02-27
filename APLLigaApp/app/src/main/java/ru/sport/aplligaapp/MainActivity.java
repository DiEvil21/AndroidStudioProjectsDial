package ru.sport.aplligaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import ru.sport.aplligaapp.fragments.calendar.CalendarFragment;
import ru.sport.aplligaapp.fragments.stats.StatsFragment;
import ru.sport.aplligaapp.fragments.wall.WallFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    ImageView imageV_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageV_logo = findViewById(R.id.imageV_logo);
        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/APLLigaApp/img/pole_horizontal.png")
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
                    .load("http://159.69.90.204/api/APLLigaApp/img/pole.png")
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
        Glide.with(imageV_logo.getContext())
                .load("http://159.69.90.204/api/APLLigaApp/img/logo.png")
                .into(imageV_logo);
        fragment = new WallFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_wall:
                fragment = new WallFragment();
                break;
            case R.id.but_stats:
                fragment = new StatsFragment();
                break;
            case R.id.but_calendar:
                fragment = new CalendarFragment();
                break;
        }

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }



}