package ru.sport.esheodnoprilojenie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApiService {
    @GET("box.json")
    Call<List<NewsModel>> getNews();
}

