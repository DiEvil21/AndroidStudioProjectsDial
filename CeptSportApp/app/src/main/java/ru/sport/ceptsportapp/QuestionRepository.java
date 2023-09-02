package ru.sport.ceptsportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionRepository {
    private ApiService apiService;

    public QuestionRepository() {
        String BASE_URL = "http://159.69.90.204/api/CeptSportApp/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Замените на реальный базовый URL вашего сервера
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<Question>> getQuestions() {
        MutableLiveData<List<Question>> questionsData = new MutableLiveData<>();
        apiService.getQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questionsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                // Обработка ошибок
            }
        });
        return questionsData;
    }
}

