package ru.sport.historicalmvvmfootball;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FactsViewModel extends ViewModel {

    public InfoApi infoApi;
    public MutableLiveData<List<FactsData>> factsData = new MutableLiveData<>();

    public LiveData<List<FactsData>> getFacts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        infoApi = retrofit.create(InfoApi.class);
        Call<List<FactsData>> call = infoApi.getFacts();
        call.enqueue(new Callback<List<FactsData>>() {
            @Override
            public void onResponse(Call<List<FactsData>> call, Response<List<FactsData>> response) {

                factsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<FactsData>> call, Throwable t) {

            }
        });

        return factsData;
    }


}
