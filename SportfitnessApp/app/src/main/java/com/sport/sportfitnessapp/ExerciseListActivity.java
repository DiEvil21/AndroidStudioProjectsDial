package com.sport.sportfitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ExerciseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private ProgressBar progressBar;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.list_back);
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
        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExerciseAdapter(); // Создайте адаптер для RecyclerView
        recyclerView.setAdapter(adapter);

        ExerciseViewModel exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getExercises().observe(this, exercises -> {
            adapter.setExercises(exercises);
        });
        progressBar = findViewById(R.id.progressBar);
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        exerciseViewModel.fetchExercises(); // Загрузите данные
    }
    @Override
    protected void onStart() {
        super.onStart();
        int savedRepetitions = prefs.getInt("repetitions", 0);
        updateProgressBar(savedRepetitions);
    }

    private void updateProgressBar(int repetitions) {
        int progress = (repetitions * 100) / 100;
        progressBar.setProgress(progress);
    }
}
