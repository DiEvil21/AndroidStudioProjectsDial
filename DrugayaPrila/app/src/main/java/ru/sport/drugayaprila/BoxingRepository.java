package ru.sport.drugayaprila;

import android.os.Debug;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoxingRepository {
    private BoxingApiService apiService;

    public BoxingRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/DrugayaPrila/") // Замените на фактический URL API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(BoxingApiService.class);
    }

    public LiveData<List<NewsModel>> getNews() {
        MutableLiveData<List<NewsModel>> newsData = new MutableLiveData<>();

        apiService.getNews().enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                // Обработка ошибки

            }
        });

        return newsData;
    }

    public LiveData<List<MatchModel>> getMatches() {
        MutableLiveData<List<MatchModel>> matchesData = new MutableLiveData<>();

        apiService.getMatches().enqueue(new Callback<List<MatchModel>>() {
            @Override
            public void onResponse(Call<List<MatchModel>> call, Response<List<MatchModel>> response) {
                if (response.isSuccessful()) {
                    matchesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MatchModel>> call, Throwable t) {
                // Обработка ошибки
            }
        });

        return matchesData;
    }
}

