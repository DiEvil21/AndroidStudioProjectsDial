package ru.sport.pockerrules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AboutViewModel extends ViewModel {

    private PockerRepository pockerRepository;

    private LiveData<List<AboutListData>> aboutListData;
    private LiveData<List<AboutListData>> aboutListData2;

    public AboutViewModel() {
        pockerRepository = new PockerRepository();
        aboutListData = pockerRepository.getAboutList();
        aboutListData2 = pockerRepository.getAboutList2();
    }

    public LiveData<List<AboutListData>> getAboutListData() {return aboutListData;}
    public LiveData<List<AboutListData>> getAboutListData2() {return aboutListData2;}
}
