package ru.sport.logoquizeapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClubApiService {
    @GET("clubs.json")
    Call<List<Club>> getClubs();
}

