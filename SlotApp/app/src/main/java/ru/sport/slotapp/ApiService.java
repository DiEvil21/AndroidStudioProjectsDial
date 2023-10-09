package ru.sport.slotapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("slots_info.json")
    Call<Data> getInfo();

    @GET("slots_combo.json")
    Call<Data> getMain();

}
