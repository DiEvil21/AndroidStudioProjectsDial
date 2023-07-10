package ru.sport.lolkekcheburek.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sport.lolkekcheburek.model.HockeyNews;
import ru.sport.lolkekcheburek.network.HockeyApiService;
import ru.sport.lolkekcheburek.network.RetrofitClient;

public class HockeyNewsViewModel extends ViewModel {
    private MutableLiveData<List<HockeyNews>> newsList;

    public LiveData<List<HockeyNews>> getNews() {
        if (newsList == null) {
            newsList = new MutableLiveData<>();
            loadNews();
        }
        return newsList;
    }

    private void loadNews() {
        HockeyApiService apiService = RetrofitClient.getClient().create(HockeyApiService.class);
        Call<List<HockeyNews>> call = apiService.getNews();
        call.enqueue(new Callback<List<HockeyNews>>() {
            @Override
            public void onResponse(Call<List<HockeyNews>> call, Response<List<HockeyNews>> response) {
                if (response.isSuccessful()) {
                    newsList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HockeyNews>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}

