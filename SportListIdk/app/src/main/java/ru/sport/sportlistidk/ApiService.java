package ru.sport.sportlistidk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/SportListIdk/list.json")
    Call<List<ListItem>> getListItems();
}
