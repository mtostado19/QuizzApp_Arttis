package com.example.room_demo_application.db

import androidx.room.*

@Dao
interface PuntuacionDAO {
    @Query("Select * FROM score")
    fun getAll() : List<Puntuacion>

    @Query("SELECT * FROM score ORDER BY score DESC LIMIT 5")
    fun getTopFive() : List<Puntuacion>

    @Query("SELECT * FROM score ORDER BY score DESC")
    fun getDesc() : List<Puntuacion>

    @Query("SELECT * FROM score ORDER BY score ASC")
    fun getAsc() : List<Puntuacion>

    @Query("SELECT * FROM score ORDER BY score DESC, hints ASC LIMIT 5")
    fun getAscFirst() : List<Puntuacion>

    @Update
    fun update(score: Puntuacion)

    @Insert
    fun insert(score: Puntuacion)

    @Delete
    fun delete(score: Puntuacion)

    @Query("DELETE FROM score WHERE user = :id")
    fun deleteSpecific(id: String)
}