package ru.sport.mvvmquize;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizViewModel extends ViewModel {
    private MutableLiveData<List<Question>> questionsLiveData = new MutableLiveData<>();
    private QuizRepository quizRepository;

    public QuizViewModel() {
        quizRepository = new QuizRepository();
    }

    public LiveData<List<Question>> getQuestionsLiveData() {
        return questionsLiveData;
    }

    public void loadQuestions() {
        quizRepository.getQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questionsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                // Обработка ошибки получения вопросов
            }
        });
    }
}

