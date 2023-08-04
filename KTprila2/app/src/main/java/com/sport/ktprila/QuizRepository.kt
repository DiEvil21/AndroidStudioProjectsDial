package com.sport.ktprila

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizRepository {
    private val api: QuizApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://159.69.90.204/api/Ktprila/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(QuizApi::class.java)
    }

    suspend fun getQuizQuestions(): List<Question> {
        return try {
            val response = api.getQuizQuestions()
            if (response.isSuccessful) {
                response.body()?.questions ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
