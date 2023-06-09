package ru.sport.sportlistidk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ListViewModel extends ViewModel {
    private ListRepository repository;
    private LiveData<List<ListItem>> listItems;

    public ListViewModel() {
        repository = new ListRepository();
        listItems = repository.getListItems();
    }

    public LiveData<List<ListItem>> getListItems() {
        return listItems;
    }
}

