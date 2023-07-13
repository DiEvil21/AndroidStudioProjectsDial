package ru.sport.newyorkrangers.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.sport.newyorkrangers.calendar.CalendarData;
import ru.sport.newyorkrangers.news.NewsData;
import ru.sport.newyorkrangers.statistics.StatisticsData;

public interface ApiService {
    @GET("news.json")
    Call<List<NewsData>> getNews();

    @GET("match.json")
    Call<List<CalendarData>> getCalendar();

    @GET("stats.json")
    Call<List<StatisticsData>> getStatistics();
}
