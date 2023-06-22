package ru.sport.sportnews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<NewsItem>> newsItems = new MutableLiveData<>(new ArrayList<>());


    public LiveData<List<NewsItem>> getNewsItems() {
        if (newsItems == null) {
            newsItems = new MutableLiveData<>();
            loadFootballNews(); // Загружаем новости футбола по умолчанию
        }
        return newsItems;
    }

    public void loadFootballNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/SportNews/") // Замените на вашу базовую URL для загрузки данных
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsService newsService = retrofit.create(NewsService.class);
        Call<List<NewsResponse>> call = newsService.getFootballNews();

        call.enqueue(new Callback<List<NewsResponse>>() {
            @Override
            public void onResponse(Call<List<NewsResponse>> call, Response<List<NewsResponse>> response) {
                if (response.isSuccessful()) {
                    List<NewsItem> newsList = convertResponseToNewsItems(response.body());
                    newsItems.setValue(newsList);
                    System.out.println("response: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<NewsResponse>> call, Throwable t) {
                // Обработка ошибки загрузки данных
            }
        });
    }


    public void loadHockeyNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/SportNews/") // Замените на вашу базовую URL для загрузки данных
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsService newsService = retrofit.create(NewsService.class);
        Call<List<NewsResponse>> call = newsService.getHockeyNews();

        call.enqueue(new Callback<List<NewsResponse>>() {
            @Override
            public void onResponse(Call<List<NewsResponse>> call, Response<List<NewsResponse>> response) {
                if (response.isSuccessful()) {
                    List<NewsItem> newsList = convertResponseToNewsItems(response.body());
                    newsItems.setValue(newsList);
                }
            }

            @Override
            public void onFailure(Call<List<NewsResponse>> call, Throwable t) {
                // Обработка ошибки загрузки данных
            }
        });
    }

    public void loadBoxingNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/SportNews/") // Замените на вашу базовую URL для загрузки данных
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsService newsService = retrofit.create(NewsService.class);
        Call<List<NewsResponse>> call = newsService.getBoxingNews();

        call.enqueue(new Callback<List<NewsResponse>>() {
            @Override
            public void onResponse(Call<List<NewsResponse>> call, Response<List<NewsResponse>> response) {
                if (response.isSuccessful()) {
                    List<NewsItem> newsList = convertResponseToNewsItems(response.body());
                    newsItems.setValue(newsList);
                }
            }

            @Override
            public void onFailure(Call<List<NewsResponse>> call, Throwable t) {
                // Обработка ошибки загрузки данных
            }
        });
    }

    private List<NewsItem> convertResponseToNewsItems(List<NewsResponse> newsResponseList) {
        List<NewsItem> newsItems = new ArrayList<>();
        for (NewsResponse newsResponse : newsResponseList) {
            String imageUrl = newsResponse.getImageUrl();
            String title = newsResponse.getTitle();
            String description = newsResponse.getDescription();
            NewsItem newsItem = new NewsItem(imageUrl, title, description);
            newsItems.add(newsItem);
        }
        return newsItems;
    }
}

