package ru.sport.casinomore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("pocker_rules.json")
    Call<List<Item>> getItems();

    @GET("roulette_rules.json")
    Call<List<Item>> getRoulette();

    @GET("pocker_combo.json")
    Call<List<Item>> getPocker();
}
