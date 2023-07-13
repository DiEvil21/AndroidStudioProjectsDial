package ru.sport.newyorkrangers.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sport.newyorkrangers.api.ApiService;
import ru.sport.newyorkrangers.api.RetrofitClient;

public class CalendarViewModel extends ViewModel {
    private MutableLiveData<List<CalendarData>> matchList;

    public LiveData<List<CalendarData>> getMatches() {
        if (matchList == null) {
            matchList = new MutableLiveData<>();
            loadMatches();
        }
        return matchList;
    }

    private void loadMatches() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<CalendarData>> call = apiService.getCalendar();
        call.enqueue(new Callback<List<CalendarData>>() {
            @Override
            public void onResponse(Call<List<CalendarData>> call, Response<List<CalendarData>> response) {
                if (response.isSuccessful()) {
                    matchList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CalendarData>> call, Throwable t) {
                // Обработка ошибок
            }
        });
    }
}
