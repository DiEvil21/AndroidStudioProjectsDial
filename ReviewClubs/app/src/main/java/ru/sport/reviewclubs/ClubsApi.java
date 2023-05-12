package ru.sport.reviewclubs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClubsApi {

    @GET("ReviewClubs/main.json")
    Call<List<Clubs>> getClubs();
}
