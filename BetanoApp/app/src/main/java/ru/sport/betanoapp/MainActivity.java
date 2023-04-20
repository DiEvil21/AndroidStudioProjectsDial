package ru.sport.betanoapp;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    ImageButton imageButton1,imageButton2,imageButton3,imageButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-------------------------------------------------------------
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_main);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/BetanoApp/background.png")
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linear.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        //-------------------------------------------------------------
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        fragment = new PageFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();

        }


    public void onClick(View v) {
        fragment = new PageFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        imageButton1.setColorFilter(ContextCompat.getColor(this, R.color.gray));
        imageButton2.setColorFilter(ContextCompat.getColor(this, R.color.gray));
        imageButton3.setColorFilter(ContextCompat.getColor(this, R.color.gray));
        imageButton4.setColorFilter(ContextCompat.getColor(this, R.color.gray));
        switch (v.getId()) {
            case R.id.imageButton1:
                bundle.putInt("id", 0);
                imageButton1.setColorFilter(ContextCompat.getColor(this, R.color.white));
                break;
            case R.id.imageButton2:
                bundle.putInt("id", 1);
                imageButton2.setColorFilter(ContextCompat.getColor(this, R.color.white));
                break;
            case R.id.imageButton3:
                bundle.putInt("id", 2);
                imageButton3.setColorFilter(ContextCompat.getColor(this, R.color.white));
                break;
            case R.id.imageButton4:
                bundle.putInt("id", 3);
                imageButton4.setColorFilter(ContextCompat.getColor(this, R.color.white));
                break;
        }
        fragment.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

}

