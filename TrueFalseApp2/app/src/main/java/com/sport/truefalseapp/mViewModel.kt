package com.sport.truefalseapp

import androidx.lifecycle.ViewModel
import okhttp3.*
import org.json.JSONArray
import org.json.JSONTokener
import java.io.IOException


private const val TAG = "myViewModel"

class myViewModel : ViewModel() {
    val questionBank = ArrayList<Question>()
    init {


    }
    var currentIndex = 0
    private var client = OkHttpClient()



    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: String
        get() = questionBank[currentIndex].question
    val currectQuestionAbout: String
        get() = questionBank[currentIndex].about
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun addNewQ(question: String,answer: Boolean, about: String) {
        questionBank.add(Question(question,answer, about))
    }
}
