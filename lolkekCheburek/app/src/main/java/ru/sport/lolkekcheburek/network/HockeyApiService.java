package ru.sport.lolkekcheburek.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.sport.lolkekcheburek.model.HockeyMatch;
import ru.sport.lolkekcheburek.model.HockeyNews;
import ru.sport.lolkekcheburek.model.HockeyStatistic;

public interface HockeyApiService {
    @GET("news.json")
    Call<List<HockeyNews>> getNews();

    @GET("match.json")
    Call<List<HockeyMatch>> getMatches();

    @GET("stats.json")
    Call<List<HockeyStatistic>> getStatistics();
}

