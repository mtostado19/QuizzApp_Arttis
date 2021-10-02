package com.example.quizapp_arttis

data class Question(val text: String, val right: String, val wrong: Array<String>, var answered: String, val topic: String)