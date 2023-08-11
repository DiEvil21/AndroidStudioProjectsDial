package com.sport.sportfitnessapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExerciseRepository {
    private ExerciseApi exerciseApi;

    public ExerciseRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/SportfitnessApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        exerciseApi = retrofit.create(ExerciseApi.class);
    }

    public Call<List<Exercise>> getExercises() {
        return exerciseApi.getExercises();
    }
}

