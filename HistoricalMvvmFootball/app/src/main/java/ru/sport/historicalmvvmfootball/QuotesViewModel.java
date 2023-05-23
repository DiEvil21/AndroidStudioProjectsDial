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

public class QuotesViewModel extends ViewModel {
    public InfoApi infoApi;
    public MutableLiveData<List<QuotesData>> quotesData = new MutableLiveData<>();


    public LiveData<List<QuotesData>> getQuotes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        infoApi = retrofit.create(InfoApi.class);
        Call<List<QuotesData>> call = infoApi.getQuotes();
        call.enqueue(new Callback<List<QuotesData>>() {
            @Override
            public void onResponse(Call<List<QuotesData>> call, Response<List<QuotesData>> response) {

                quotesData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<QuotesData>> call, Throwable t) {

            }
        });

        return quotesData;
    }


}
