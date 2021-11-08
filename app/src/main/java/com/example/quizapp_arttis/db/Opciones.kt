package com.example.room_demo_application.db

import androidx.room.*

@Entity(tableName = "options")
data class Opciones(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "numQuestions") val numQuestions: Int,
    @ColumnInfo(name = "categories") val categories: String,
    @ColumnInfo(name = "clues") val clues: Boolean,
    @ColumnInfo(name = "user") val user: String

)