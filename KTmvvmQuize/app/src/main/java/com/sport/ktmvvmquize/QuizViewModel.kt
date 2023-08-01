package com.sport.ktmvvmquize

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers


class QuizViewModel(private val api: HockeyQuizApi) : ViewModel() {
    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _currentQuestionIndex = MutableLiveData<Int>().apply { value = 0 }
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

    val currentQuestion: LiveData<Question?> = currentQuestionIndex.map { index ->
        val questionsValue = questions.value
        if (index in (questionsValue?.indices ?: emptyList())) {
            questionsValue?.get(index)
        } else {
            null
        }
    }
    fun isLastQuestion(): Boolean {
        val questions = _questions.value
        val currentQuestionIndex = _currentQuestionIndex.value ?: 0
        return questions != null && currentQuestionIndex == questions.size - 1
    }

    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val questions = api.getQuestions()
                Log.d("QuizViewModel", "Fetched questions: $questions")
                _questions.value = questions
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error fetching questions: ${e.message}")
            }
        }
    }

    fun moveToNextQuestion() {
        _currentQuestionIndex.value = (_currentQuestionIndex.value ?: 0) + 1
    }
}

