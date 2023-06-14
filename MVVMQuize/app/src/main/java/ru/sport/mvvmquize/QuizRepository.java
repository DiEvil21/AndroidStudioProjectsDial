package ru.sport.mvvmquize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizRepository {
    private QuizApi quizApi;

    public QuizRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/MVVMQuize/") // Замените на базовый URL вашего сервера
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        quizApi = retrofit.create(QuizApi.class);
    }

    public Call<List<Question>> getQuestions() {
        return quizApi.getQuestions();
    }
}

