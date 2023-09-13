package ru.sport.boxquizeapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class QuizViewModel extends ViewModel {
    private QuizRepository quizRepository;
    private LiveData<List<Question>> questionsLiveData;

    public QuizViewModel() {
        quizRepository = new QuizRepository();
        questionsLiveData = quizRepository.getQuestions();
    }

    public LiveData<List<Question>> getQuestionsLiveData() {
        return questionsLiveData;
    }
}
