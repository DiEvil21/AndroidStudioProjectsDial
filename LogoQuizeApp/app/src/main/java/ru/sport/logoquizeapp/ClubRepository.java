package ru.sport.logoquizeapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubRepository {
    private ClubApiService apiService;

    public ClubRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/LogoQuizeApp/") // Замените на вашу базовую URL-адрес сервера
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ClubApiService.class);
    }

    public LiveData<List<Club>> getClubs() {
        MutableLiveData<List<Club>> clubsLiveData = new MutableLiveData<>();

        apiService.getClubs().enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
                if (response.isSuccessful()) {
                    clubsLiveData.setValue(response.body());
                    System.out.println(response.body());
                } else {
                    // Обработка ошибки
                }
            }

            @Override
            public void onFailure(Call<List<Club>> call, Throwable t) {
                // Обработка ошибки
            }
        });

        return clubsLiveData;
    }
}

