package ru.sport.clubquize;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizViewModel extends ViewModel {
    private ApiService apiService;
    private MutableLiveData<List<Club>> clubsLiveData;
    private int currentQuestionIndex;

    public QuizViewModel() {
        apiService = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/LogoQuize/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);

        clubsLiveData = new MutableLiveData<>();
        currentQuestionIndex = -1;
    }

    public LiveData<List<Club>> getClubsLiveData() {
        return clubsLiveData;
    }

    public void loadClubs() {
        apiService.getClubs().enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
                if (response.isSuccessful()) {
                    List<Club> clubs = response.body();
                    clubsLiveData.setValue(clubs);
                } else {
                    // Обработка ошибки при загрузке данных
                }
            }

            @Override
            public void onFailure(Call<List<Club>> call, Throwable t) {
                // Обработка ошибки при загрузке данных
            }
        });
    }

    public Club getNextQuestion() {
        List<Club> clubs = clubsLiveData.getValue();
        if (clubs != null && !clubs.isEmpty()) {
            int randomIndex = new Random().nextInt(clubs.size());
            return clubs.get(randomIndex);
        } else {
            return null; // Все вопросы исчерпаны
        }
    }
}


