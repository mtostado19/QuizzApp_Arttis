package com.example.quizapp_arttis.db

import androidx.room.*

@Entity(tableName = "score")
data class Puntuacion(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "hints") val hints: Int,
    @ColumnInfo(name = "user") val user: String,

)