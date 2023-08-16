package com.sport.workoutgenerator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteViewModel extends ViewModel {
    private MutableLiveData<ImageData> imageData = new MutableLiveData<>();
    private List<ImageData> imageList = new ArrayList<>();
    private Random random = new Random();

    public LiveData<ImageData> getImageData() {
        return imageData;
    }

    public void startRoulette() {
        int randomIndex = random.nextInt(imageList.size());
        imageData.setValue(imageList.get(randomIndex));
    }

    public void setImageList(List<ImageData> images) {
        imageList = images;
    }
}

