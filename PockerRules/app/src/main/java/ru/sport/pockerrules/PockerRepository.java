package ru.sport.pockerrules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PockerRepository {


    private ApiService apiService;

    public PockerRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/PockerRules/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<MenuListData>> getMenuList() {
        MutableLiveData<List<MenuListData>> menuListLiveData = new MutableLiveData<>();

        apiService.getMenuList().enqueue(new Callback<List<MenuListData>>() {
            @Override
            public void onResponse(Call<List<MenuListData>> call, Response<List<MenuListData>> response) {
                menuListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuListData>> call, Throwable t) {

            }
        });
        return menuListLiveData;
    }

    public LiveData<List<AboutListData>> getAboutList() {
        MutableLiveData<List<AboutListData>> aboutListLiveData = new MutableLiveData<>();

        apiService.getAboutList().enqueue(new Callback<List<AboutListData>>() {
            @Override
            public void onResponse(Call<List<AboutListData>> call, Response<List<AboutListData>> response) {
                aboutListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<AboutListData>> call, Throwable t) {

            }
        });
        return  aboutListLiveData;
    }
    public LiveData<List<AboutListData>> getAboutList2() {
        MutableLiveData<List<AboutListData>> aboutListLiveData = new MutableLiveData<>();

        apiService.getAboutList2().enqueue(new Callback<List<AboutListData>>() {
            @Override
            public void onResponse(Call<List<AboutListData>> call, Response<List<AboutListData>> response) {
                aboutListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<AboutListData>> call, Throwable t) {

            }
        });
        return  aboutListLiveData;
    }
}
