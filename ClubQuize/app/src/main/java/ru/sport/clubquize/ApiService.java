package ru.sport.clubquize;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("logos.json")
    Call<List<Club>> getClubs();
}

