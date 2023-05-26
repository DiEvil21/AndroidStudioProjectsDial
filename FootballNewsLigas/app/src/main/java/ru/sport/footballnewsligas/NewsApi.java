package ru.sport.footballnewsligas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.sport.footballnewsligas.fragments_logic.NewsData;


public interface NewsApi {

    @GET("FootballNewsLigas/news.json")
    Call<List<NewsData>> getNewsLeft();

    @GET("FootballNewsLigas/news2.json")
    Call<List<NewsData>> getNewsRight();
}
