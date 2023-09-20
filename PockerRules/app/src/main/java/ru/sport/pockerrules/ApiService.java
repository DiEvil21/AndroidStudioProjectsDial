package ru.sport.pockerrules;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("pocker_menu.json")
    Call<List<MenuListData>> getMenuList();

    @GET("pocker_rules.json")
    Call<List<AboutListData>> getAboutList();

    @GET("pocker_combo.json")
    Call<List<AboutListData>> getAboutList2();
}
