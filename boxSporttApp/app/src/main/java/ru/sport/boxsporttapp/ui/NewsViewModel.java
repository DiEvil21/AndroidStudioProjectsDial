package ru.sport.boxsporttapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sport.boxsporttapp.ApiService;
import ru.sport.boxsporttapp.RetrofitClient;


public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<NewsData>> newsList;

    public LiveData<List<NewsData>> getNews() {
        if (newsList == null) {
            newsList = new MutableLiveData<>();
            loadNews();
        }
        return newsList;
    }

    private void loadNews() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<NewsData>> call = apiService.getNews();
        call.enqueue(new Callback<List<NewsData>>() {
            @Override
            public void onResponse(Call<List<NewsData>> call, Response<List<NewsData>> response) {
                if (response.isSuccessful()) {
                    newsList.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<NewsData>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}
