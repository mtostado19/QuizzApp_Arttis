package com.example.room_demo_application.db

import androidx.room.*

@Entity(tableName = "activeGames")
data class JuegoActual(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "questions") val questions: String,
    @ColumnInfo(name = "options") val options: String,
    @ColumnInfo(name = "gameValues") val gameValues: String,
    @ColumnInfo(name = "user") val user: String,
)
