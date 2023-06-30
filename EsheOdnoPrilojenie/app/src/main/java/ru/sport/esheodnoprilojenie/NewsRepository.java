package ru.sport.esheodnoprilojenie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private NewsApiService apiService;

    public NewsRepository() {
        apiService = ApiClient.getClient().create(NewsApiService.class);
    }

    public LiveData<List<NewsModel>> getNews() {
        MutableLiveData<List<NewsModel>> newsData = new MutableLiveData<>();

        apiService.getNews().enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                newsData.setValue(null);
            }
        });

        return newsData;
    }
}

