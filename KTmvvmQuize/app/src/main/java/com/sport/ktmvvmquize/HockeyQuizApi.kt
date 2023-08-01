package com.sport.ktmvvmquize

import retrofit2.http.GET

interface HockeyQuizApi {
    @GET("quize.json")
    suspend fun getQuestions(): List<Question>
}
