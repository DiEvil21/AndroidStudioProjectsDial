package ru.sport.casinomore;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private ApiService apiService;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/CasinoMore/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<Item>> getItems() {
        MutableLiveData<List<Item>> menuListLiveData = new MutableLiveData<>();

        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                menuListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        return menuListLiveData;
    }

    public LiveData<List<Item>> getRoulette() {
        MutableLiveData<List<Item>> menuListLiveData = new MutableLiveData<>();

        apiService.getRoulette().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                menuListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        return menuListLiveData;
    }
    public LiveData<List<Item>> getPocker() {
        MutableLiveData<List<Item>> menuListLiveData = new MutableLiveData<>();

        apiService.getPocker().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                menuListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        return menuListLiveData;
    }
}