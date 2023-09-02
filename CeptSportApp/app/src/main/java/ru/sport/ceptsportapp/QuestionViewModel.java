package ru.sport.ceptsportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class QuestionViewModel extends ViewModel {
    private QuestionRepository repository;
    private LiveData<List<Question>> questionsLiveData;

    public QuestionViewModel() {
        repository = new QuestionRepository();
        questionsLiveData = repository.getQuestions();
    }

    public LiveData<List<Question>> getQuestionsLiveData() {
        return questionsLiveData;
    }
}

