package ru.sport.drugayaprila;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BoxingApiService {
    @GET("news.json")
    Call<List<NewsModel>> getNews();

    @GET("matches.json")
    Call<List<MatchModel>> getMatches();
}

