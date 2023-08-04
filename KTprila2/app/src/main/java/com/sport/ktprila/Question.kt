package com.sport.ktprila

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctOptionIndex: Int
)