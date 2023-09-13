package ru.sport.boxquizeapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizRepository {
    private QuizService quizService;

    public QuizRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/BoxQuizeApp/") // Замените на ваш URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        quizService = retrofit.create(QuizService.class);
    }

    public LiveData<List<Question>> getQuestions() {
        MutableLiveData<List<Question>> questionsLiveData = new MutableLiveData<>();

        quizService.getQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questionsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                // Обработка ошибок
            }
        });

        return questionsLiveData;
    }
}
