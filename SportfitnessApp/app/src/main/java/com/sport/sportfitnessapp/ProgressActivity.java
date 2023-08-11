package com.sport.sportfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProgressActivity extends AppCompatActivity {

    private int existingRepetitions = 0;
    private SharedPreferences prefs;
    private List<Exercise> exercises = new ArrayList<>();
    private Spinner spinnerExercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.progress_back);
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
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        existingRepetitions = prefs.getInt("repetitions", 0);
        spinnerExercises = findViewById(R.id.spinnerExercises);
        EditText editRepetitions = findViewById(R.id.editRepetitions);
        Button buttonSaveProgress = findViewById(R.id.buttonSaveProgress);
        // Инициализация Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/SportfitnessApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExerciseApi exerciseService = retrofit.create(ExerciseApi.class);
        Call<List<Exercise>> call = exerciseService.getExercises();

        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    exercises = response.body();
                    List<String> exerciseNames = new ArrayList<>();
                    for (Exercise exercise : exercises) {
                        exerciseNames.add(exercise.getName());
                    }
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ProgressActivity.this, android.R.layout.simple_spinner_item, exerciseNames);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerExercises.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                // Обработка ошибки запроса
            }
        });
        buttonSaveProgress.setOnClickListener(v -> {
            int repetitionsToAdd = Integer.parseInt(editRepetitions.getText().toString());
            existingRepetitions += repetitionsToAdd;
            updateProgressBar();
            prefs.edit().putInt("repetitions", existingRepetitions).apply();
            editRepetitions.setText(""); // Сбросить поле ввода

            // Дополнительная логика сохранения прогресса
        });

        updateProgressBar();
    }

    private void updateProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        int progress = (existingRepetitions * 100) / 100; // Предположим, 100 - максимальное значение
        progressBar.setProgress(progress);
    }
}

