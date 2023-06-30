package ru.sport.esheodnoprilojenie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<List<NewsModel>> newsData;

    public NewsViewModel() {
        newsRepository = new NewsRepository();
    }

    public LiveData<List<NewsModel>> getNews() {
        if (newsData == null) {
            newsData = (MutableLiveData<List<NewsModel>>) newsRepository.getNews();
        }
        return newsData;
    }
}

