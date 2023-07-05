package ru.sport.drugayaprila;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private BoxingRepository repository;
    private LiveData<List<NewsModel>> newsData;

    public NewsViewModel() {
        repository = new BoxingRepository();
        newsData = repository.getNews();
    }

    public LiveData<List<NewsModel>> getNewsData() {
        return newsData;
    }
}

