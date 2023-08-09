package com.sport.ppfitnessapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<Workout> {
    private SharedPreferences sharedPreferences;

    public WorkoutAdapter(Context context, List<Workout> workoutList, SharedPreferences prefs) {
        super(context, 0, workoutList);
        sharedPreferences = prefs;
    }

    @NonNull
    @Override
    public View getView(final int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_workout, parent, false);
        }

        final Workout workout = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);

        final Button deleteButton = convertView.findViewById(R.id.deleteButton);

        nameTextView.setText(workout.getName());
        dateTextView.setText(workout.getDate());
        descriptionTextView.setText(workout.getDescription());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDeleteButton(deleteButton);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(workout);
                removeWorkoutFromPrefs(workout.getName());
                notifyDataSetChanged();

            }
        });

        return convertView;
    }
    private void removeWorkoutFromPrefs(String workoutName) {
        List<Workout> savedWorkouts = Workout.fromJsonArray(sharedPreferences.getString("workouts", ""));

        // Ищем тренировку по имени и удаляем её
        Workout workoutToRemove = null;
        for (Workout workout : savedWorkouts) {
            if (workout.getName().equals(workoutName)) {
                workoutToRemove = workout;
                break;
            }
        }

        if (workoutToRemove != null) {
            savedWorkouts.remove(workoutToRemove);
            String updatedWorkoutsJson = Workout.toJsonArray(savedWorkouts);
            sharedPreferences.edit().putString("workouts", updatedWorkoutsJson).apply();
            Log.d("WorkoutAdapter", "Workout removed from prefs: " + workoutToRemove.getName());
            Log.d("WorkoutAdapter", "Updated workouts: " + updatedWorkoutsJson);
            notifyDataSetChanged();
        } else {
            Log.d("WorkoutAdapter", "Workout not found: " + workoutName);
        }
    }


    private void toggleDeleteButton(Button deleteButton) {
        if (deleteButton.getVisibility() == View.VISIBLE) {
            deleteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
        }
    }
}



