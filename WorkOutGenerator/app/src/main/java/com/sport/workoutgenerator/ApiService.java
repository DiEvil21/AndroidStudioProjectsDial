package com.sport.workoutgenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("workouts.json")
    Call<List<ImageData>> getImageData();
}

