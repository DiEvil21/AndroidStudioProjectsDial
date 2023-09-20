package ru.sport.pockerrules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MenuViewModel extends ViewModel {


    private PockerRepository pockerRepository;
    private LiveData<List<MenuListData>> menuListData;

    public MenuViewModel() {
        pockerRepository = new PockerRepository();
        menuListData = pockerRepository.getMenuList();
    }

    public LiveData<List<MenuListData>> getMenuListData() {return menuListData;}


}
