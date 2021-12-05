package com.example.room_demo_application.db

import androidx.room.*

@Entity(tableName = "options")
data class Opciones(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "difficulty") var difficulty: Int,
    @ColumnInfo(name = "numQuestions") var numQuestions: Int,
    @ColumnInfo(name = "categories") var categories: String,
    @ColumnInfo(name = "clues") var clues: Boolean,
    @ColumnInfo(name = "user") var user: String

)