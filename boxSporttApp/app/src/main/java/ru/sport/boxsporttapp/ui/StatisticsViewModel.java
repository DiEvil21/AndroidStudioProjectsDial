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

public class StatisticsViewModel extends ViewModel {
    private MutableLiveData<List<StatisticsData>> statisticList;

    public LiveData<List<StatisticsData>> getStatistics() {
        if (statisticList == null) {
            statisticList = new MutableLiveData<>();
            loadStatistics();
        }
        return statisticList;
    }

    private void loadStatistics() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<StatisticsData>> call = apiService.getStatistics();
        call.enqueue(new Callback<List<StatisticsData>>() {
            @Override
            public void onResponse(Call<List<StatisticsData>> call, Response<List<StatisticsData>> response) {
                if (response.isSuccessful()) {
                    statisticList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<StatisticsData>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}
