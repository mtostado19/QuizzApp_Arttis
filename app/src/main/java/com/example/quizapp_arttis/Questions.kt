package com.example.quizapp_arttis

data class Question(var text: String, var right: String, var values: List<String>, var answered: String, var topic: String, var hintUsed: Boolean = false)