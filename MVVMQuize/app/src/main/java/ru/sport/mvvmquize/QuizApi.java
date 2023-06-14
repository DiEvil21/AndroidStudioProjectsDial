package ru.sport.mvvmquize;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizApi {
    @GET("quize.json") // Замените "quiz" на путь к вашему JSON-файлу
    Call<List<Question>> getQuestions();
}

