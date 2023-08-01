package com.sport.ktmvvmquize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class QuizViewModelFactory(private val api: HockeyQuizApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
