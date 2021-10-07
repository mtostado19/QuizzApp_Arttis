package com.example.quizapp_arttis

data class Question(val text: String, val right: String, val values: List<String>, var answered: String, val topic: String, var hintUsed: Boolean = false)