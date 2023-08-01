package com.sport.ktmvvmquize

data class Question(
    val id: Int,
    val questionText: String,
    val imageUrl: String?,
    val correctAnswer: Boolean
)
