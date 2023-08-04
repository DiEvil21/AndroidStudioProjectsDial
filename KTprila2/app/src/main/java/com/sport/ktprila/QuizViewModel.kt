package com.sport.ktprila

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val quizRepository = QuizRepository()
    val quizQuestions = MutableLiveData<List<Question>>()

    fun loadQuizQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            val questions = quizRepository.getQuizQuestions()
            quizQuestions.postValue(questions)
        }
    }
}
