package ru.sport.mvvmfootballnews;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;

public interface NewsApi {

    @GET("MvvmFootballNews/news.json")
    Call<List<NewsData>> getNews();
}
