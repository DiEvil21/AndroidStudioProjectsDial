package ru.sport.lolkekcheburek.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sport.lolkekcheburek.model.HockeyNews;
import ru.sport.lolkekcheburek.model.HockeyStatistic;
import ru.sport.lolkekcheburek.network.HockeyApiService;
import ru.sport.lolkekcheburek.network.RetrofitClient;

public class HockeyStatisticViewModel extends ViewModel {
    private MutableLiveData<List<HockeyStatistic>> statisticList;

    public LiveData<List<HockeyStatistic>> getStatistics() {
        if (statisticList == null) {
            statisticList = new MutableLiveData<>();
            loadStatistics();
        }
        return statisticList;
    }

    private void loadStatistics() {
        HockeyApiService apiService = RetrofitClient.getClient().create(HockeyApiService.class);
        Call<List<HockeyStatistic>> call = apiService.getStatistics();
        call.enqueue(new Callback<List<HockeyStatistic>>() {
            @Override
            public void onResponse(Call<List<HockeyStatistic>> call, Response<List<HockeyStatistic>> response) {
                if (response.isSuccessful()) {
                    statisticList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HockeyStatistic>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}