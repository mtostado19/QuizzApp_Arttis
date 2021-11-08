package com.example.room_demo_application.db

import androidx.room.*

@Dao
interface PuntuacionDAO {
    @Query("Select * FROM score")
    fun getAll() : List<Puntuacion>

    @Update
    fun update(score: Puntuacion)

    @Insert
    fun insert(score: Puntuacion)

    @Delete
    fun delete(score: Puntuacion)

    @Query("DELETE FROM score WHERE user = :id")
    fun deleteSpecific(id: String)
}