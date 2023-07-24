package ru.sport.hockeyquize;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuizeViewModel extends ViewModel {
    private MutableLiveData<List<QuestionData>> questionsList;
    int QuestionID;
    public MutableLiveData<List<QuestionData>> getQuiestions() {
        if (questionsList == null) {
            questionsList = new MutableLiveData<>();
            loadQuestions();
        }


        return questionsList;

    }

    public String getNextQuestion() {
        Random rnd = new Random();
        QuestionID = rnd.nextInt(questionsList.getValue().size());
        String question = questionsList.getValue().get(QuestionID).getQuestion();
        return question;
    }
    public boolean getAnswer() {
        Boolean answer = questionsList.getValue().get(QuestionID).getAnswer();
        return answer;
    }
    private void loadQuestions() {
        ApiInterface apiService = RetrofitClient.getClient().create(ApiInterface.class);
        Call<List<QuestionData>> call =apiService.getQuestions();
        call.enqueue(new Callback<List<QuestionData>>() {
            @Override
            public void onResponse(Call<List<QuestionData>> call, Response<List<QuestionData>> response) {
                questionsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<QuestionData>> call, Throwable t) {

            }
        });

    }
}
