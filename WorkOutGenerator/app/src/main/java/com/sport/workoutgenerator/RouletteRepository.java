package com.sport.workoutgenerator;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouletteRepository {
    private static RouletteRepository instance;
    private ApiService apiService;

    private RouletteRepository() {
        apiService = RetrofitClient.createService(ApiService.class);
    }

    public static RouletteRepository getInstance() {
        if (instance == null) {
            instance = new RouletteRepository();
        }
        return instance;
    }

    public LiveData<List<ImageData>> getImageData() {
        MutableLiveData<List<ImageData>> data = new MutableLiveData<>();
        Call<List<ImageData>> call = apiService.getImageData();
        call.enqueue(new Callback<List<ImageData>>() {
            @Override
            public void onResponse(Call<List<ImageData>> call, Response<List<ImageData>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ImageData>> call, Throwable t) {
                // Обработка ошибки
            }
        });
        return data;
    }
}

