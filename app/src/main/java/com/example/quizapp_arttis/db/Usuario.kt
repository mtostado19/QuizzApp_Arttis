package com.example.quizapp_arttis.db

import androidx.room.*

@Entity(tableName = "users", indices = [Index(value = ["name"], unique = true)])
data class Usuario(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "active") val active: String
)