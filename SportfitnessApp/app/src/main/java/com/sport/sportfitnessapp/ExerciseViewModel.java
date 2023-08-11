package com.sport.sportfitnessapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseViewModel extends ViewModel {
    private MutableLiveData<List<Exercise>> exercises = new MutableLiveData<>();
    private ExerciseRepository repository = new ExerciseRepository();

    public LiveData<List<Exercise>> getExercises() {
        return exercises;
    }

    public void fetchExercises() {
        repository.getExercises().enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    exercises.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}

