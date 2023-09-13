package ru.sport.boxsporttapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.sport.boxsporttapp.ui.NewsData;
import ru.sport.boxsporttapp.ui.StatisticsData;

public interface ApiService {
    @GET("boxNews.json")
    Call<List<NewsData>> getNews();

    @GET("boxStats.json")
    Call<List<StatisticsData>> getStatistics();
}