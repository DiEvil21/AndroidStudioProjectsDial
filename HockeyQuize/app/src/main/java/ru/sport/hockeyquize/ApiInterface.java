package ru.sport.hockeyquize;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("questions.json")
    Call<List<QuestionData>> getQuestions();

}
