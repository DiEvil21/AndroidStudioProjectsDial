package ru.sport.logoquizeapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ClubViewModel extends ViewModel {
    private ClubRepository clubRepository;
    private List<Club> clubs;
    private int currentClubIndex = 0;
    private MutableLiveData<Club> currentClubLiveData = new MutableLiveData<>();
    private Context context;

    private MutableLiveData<Boolean> allClubsResolvedLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getAllClubsResolvedLiveData() {
        return allClubsResolvedLiveData;
    }

    private void checkAllClubsResolved() {
        if (currentClubIndex >= clubs.size()) {
            allClubsResolvedLiveData.setValue(true);
        }
    }


    public ClubViewModel() {
        clubRepository = new ClubRepository();
        loadClubs();
    }

    private void loadClubs() {
        clubRepository.getClubs().observeForever(clubs -> {
            this.clubs = clubs;
            if (clubs != null && clubs.size() > 0) {
                currentClubLiveData.setValue(clubs.get(0));
            }
        });
    }

    public LiveData<Club> getCurrentClubLiveData() {
        return currentClubLiveData;
    }

    public void checkAnswer(String answer) {
        Club currentClub = currentClubLiveData.getValue();
        if (currentClub != null) {
            if (currentClub.getName().equalsIgnoreCase(answer)) {
                nextClub();
            } else {
                // Обработка неправильного ответа
            }
        }
        checkAllClubsResolved();
    }

    public void nextClub() {
        currentClubIndex++;
        if (currentClubIndex < clubs.size()) {
            currentClubLiveData.setValue(clubs.get(currentClubIndex));
        } else {

        }
        checkAllClubsResolved();
    }
}

