package ru.sport.lolkekcheburek.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sport.lolkekcheburek.model.HockeyMatch;
import ru.sport.lolkekcheburek.network.HockeyApiService;
import ru.sport.lolkekcheburek.network.RetrofitClient;

public class HockeyMatchViewModel extends ViewModel {
    private MutableLiveData<List<HockeyMatch>> matchList;

    public LiveData<List<HockeyMatch>> getMatches() {
        if (matchList == null) {
            matchList = new MutableLiveData<>();
            loadMatches();
        }
        return matchList;
    }

    private void loadMatches() {
        HockeyApiService apiService = RetrofitClient.getClient().create(HockeyApiService.class);
        Call<List<HockeyMatch>> call = apiService.getMatches();
        call.enqueue(new Callback<List<HockeyMatch>>() {
            @Override
            public void onResponse(Call<List<HockeyMatch>> call, Response<List<HockeyMatch>> response) {
                if (response.isSuccessful()) {
                    matchList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HockeyMatch>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}

