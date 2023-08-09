package com.sport.ppfitnessapp;

import androidx.annotation.Nullable;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import android.content.SharedPreferences;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class MainActivity extends AppCompatActivity {

    private List<Workout> workoutList = new ArrayList<>();
    private WorkoutAdapter adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
        loadWorkoutsFromPrefs();

        ListView workoutListView = findViewById(R.id.workoutListView);
        adapter = new WorkoutAdapter(this, workoutList, sharedPreferences);
        workoutListView.setAdapter(adapter);

        Button addWorkoutButton = findViewById(R.id.addWorkoutButton);
        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWorkoutActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void loadWorkoutsFromPrefs() {
        String workoutsJson = sharedPreferences.getString("workouts", "");
        if (!workoutsJson.isEmpty()) {
            List<Workout> savedWorkouts = Workout.fromJsonArray(workoutsJson);

            // Очищаем текущий список и добавляем только существующие тренировки
            workoutList.clear();
            for (Workout workout : savedWorkouts) {
                if (!workoutList.contains(workout)) {
                    workoutList.add(workout);
                }
            }
        }
    }


    private void saveWorkoutsToPrefs() {
        String workoutsJson = Workout.toJsonArray(workoutList);
        sharedPreferences.edit().putString("workouts", workoutsJson).apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Workout newWorkout = (Workout) data.getSerializableExtra("newWorkout");
            workoutList.add(newWorkout);
            adapter.notifyDataSetChanged();
            saveWorkoutsToPrefs();
        }
    }
}

