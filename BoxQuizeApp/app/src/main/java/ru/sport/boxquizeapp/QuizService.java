package ru.sport.boxquizeapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizService {
    @GET("boxquize.json")
    Call<List<Question>> getQuestions();
}
