package ru.sport.chelsiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import ru.sport.chelsiapp.fragments.matchs.MatchsFragment;
import ru.sport.chelsiapp.fragments.news.NewsFragment;
import ru.sport.chelsiapp.fragments.stats.StatsFragment;

public class MainActivity extends AppCompatActivity {
    Button but_news, but_machs, but_stats;
    FrameLayout frame;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but_news = findViewById(R.id.but_news);
        but_machs = findViewById(R.id.but_machs);
        but_stats = findViewById(R.id.but_stat);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String myUrl = prefs.getString("url", null);
        url = myUrl;

        fragment = new NewsFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
        but_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new NewsFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame,fragment);
                ft.commit();
            }
        });
        but_machs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new MatchsFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame,fragment);
                ft.commit();
            }
        });
        but_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new StatsFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame,fragment);
                ft.commit();
            }
        });
        ImageView logo = findViewById(R.id.imageView);
        Glide.with(logo.getContext())
                .load(url + "/logo.png")
                .into(logo);









    int orientation = getResources().getConfiguration().orientation;
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    LinearLayout linear = findViewById(R.id.linear_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        // In landscape
        Glide.with(MainActivity.this)
                .load(url +"img/pole_horizontal.png")
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
                .load(url +"img/pole.png")
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








}}