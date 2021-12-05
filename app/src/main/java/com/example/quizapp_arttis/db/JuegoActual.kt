package com.example.room_demo_application.db

import androidx.room.*

@Entity(tableName = "activeGames")
data class JuegoActual(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "questions") var questions: String,
    @ColumnInfo(name = "options") var options: String,
    @ColumnInfo(name = "gameValues") var gameValues: String,
    @ColumnInfo(name = "user") var user: String,
)
