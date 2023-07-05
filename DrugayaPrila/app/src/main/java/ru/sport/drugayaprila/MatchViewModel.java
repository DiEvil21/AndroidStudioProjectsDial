package ru.sport.drugayaprila;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MatchViewModel extends ViewModel {
    private BoxingRepository repository;
    private LiveData<List<MatchModel>> matchesData;

    public MatchViewModel() {
        repository = new BoxingRepository();
        matchesData = repository.getMatches();
    }

    public LiveData<List<MatchModel>> getMatchesData() {
        return matchesData;
    }
}

