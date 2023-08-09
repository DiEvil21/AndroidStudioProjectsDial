package com.sport.ppfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;


public class AddWorkoutActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        sharedPreferences = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE);
        final EditText workoutNameEditText = findViewById(R.id.workoutNameEditText);
        final EditText workoutDateEditText = findViewById(R.id.workoutDateEditText);
        final EditText workoutDescriptionEditText = findViewById(R.id.workoutDescriptionEditText);
        Button saveWorkoutButton = findViewById(R.id.saveWorkoutButton);
        saveWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = workoutNameEditText.getText().toString();
                String date = workoutDateEditText.getText().toString();
                String description = workoutDescriptionEditText.getText().toString();

                // Создайте объект Workout
                Workout newWorkout = new Workout(name, date, description);

                // Сохраните тренировку
                saveWorkout(newWorkout);

                // Откройте MainActivity
                Intent intent = new Intent(AddWorkoutActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void saveWorkout(Workout workout) {
        List<Workout> savedWorkouts = Workout.fromJsonArray(sharedPreferences.getString("workouts", ""));
        savedWorkouts.add(workout);
        String updatedWorkoutsJson = Workout.toJsonArray(savedWorkouts);
        sharedPreferences.edit().putString("workouts", updatedWorkoutsJson).apply();
    }
}

