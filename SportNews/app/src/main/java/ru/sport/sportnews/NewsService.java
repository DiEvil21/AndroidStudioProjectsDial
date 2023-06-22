package ru.sport.sportnews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("football/football.json") // Замените на путь к API, возвращающему новости футбола
    Call<List<NewsResponse>> getFootballNews();

    @GET("hok/hok.json") // Замените на путь к API, возвращающему новости хоккея
    Call<List<NewsResponse>> getHockeyNews();

    @GET("box/box.json") // Замените на путь к API, возвращающему новости бокса
    Call<List<NewsResponse>> getBoxingNews();
}

