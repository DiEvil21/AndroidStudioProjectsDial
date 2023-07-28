package com.sport.ktsportapp

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("news.json")
    suspend fun getNews(): Response<List<News>>

    @GET("stats.json")
    suspend fun getHockeyStats(): Response<List<HockeyStat>>
}
