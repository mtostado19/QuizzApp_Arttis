package com.example.room_demo_application.db

import androidx.room.*

@Dao
interface JuegoActualDAO {
    @Query("Select * FROM activeGames")
    fun getAll() : List<JuegoActual>

    @Query("SELECT * FROM activeGames WHERE user = :name")
    fun getSpecific(name: String) : List<JuegoActual>

    @Update
    fun update(juego: JuegoActual)

    @Insert
    fun insert(juego: JuegoActual)

    @Delete
    fun delete(juego: JuegoActual)

    @Query("DELETE FROM activeGames WHERE user = :id")
    fun deleteSpecific(id: String)
}