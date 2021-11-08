package com.example.room_demo_application.db

import androidx.room.*

@Dao
interface OpcionesDAO {
    @Query("Select * FROM options")
    fun getAll() : List<Opciones>

    @Update
    fun update(options: Opciones)

    @Insert
    fun insert(options: Opciones)

    @Delete
    fun delete(options: Opciones)

    @Query("DELETE FROM options WHERE user = :id")
    fun deleteSpecific(id: String)
}