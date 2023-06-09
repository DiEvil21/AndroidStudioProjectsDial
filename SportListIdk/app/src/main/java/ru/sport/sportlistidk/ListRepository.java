package ru.sport.sportlistidk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListRepository {
    private ApiService apiService;

    public ListRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<ListItem>> getListItems() {
        MutableLiveData<List<ListItem>> data = new MutableLiveData<>();

        apiService.getListItems().enqueue(new Callback<List<ListItem>>() {
            @Override
            public void onResponse(Call<List<ListItem>> call, Response<List<ListItem>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ListItem>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}

