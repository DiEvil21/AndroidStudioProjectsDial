package ru.sport.footballnewsligas;

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
import ru.sport.footballnewsligas.fragments_logic.NewsData;


public class NewsViewModel extends ViewModel {
    public NewsApi newsApi;
    public MutableLiveData<List<NewsData>> leftNewsData = new MutableLiveData<>();
    public MutableLiveData<List<NewsData>> rightNewsData = new MutableLiveData<>();


    public LiveData<List<NewsData>> getNewsLeft() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsApi = retrofit.create(NewsApi.class);
        Call<List<NewsData>> call = newsApi.getNewsLeft();
        call.enqueue(new Callback<List<NewsData>>() {
            @Override
            public void onResponse(Call<List<NewsData>> call, Response<List<NewsData>> response) {

                leftNewsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsData>> call, Throwable t) {

            }
        });

        return leftNewsData;
    }

    public LiveData<List<NewsData>> getNewsRight() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsApi = retrofit.create(NewsApi.class);
        Call<List<NewsData>> call = newsApi.getNewsRight();
        call.enqueue(new Callback<List<NewsData>>() {
            @Override
            public void onResponse(Call<List<NewsData>> call, Response<List<NewsData>> response) {

                rightNewsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsData>> call, Throwable t) {

            }
        });

        return rightNewsData;
    }
}
