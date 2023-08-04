package com.sport.ktprila

import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {
    @GET("questions.json")
    suspend fun getQuizQuestions(): Response<QuizResponse>
}