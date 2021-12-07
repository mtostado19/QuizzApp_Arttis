package com.example.room_demo_application.db

import androidx.room.*
import com.example.quizapp_arttis.db.Puntuacion

@Dao
interface OpcionesDAO {
    @Query("Select * FROM options")
    fun getAll() : List<Opciones>

    @Query("Select * FROM options WHERE user = :user")
    fun getSpecific(user: String) : List<Opciones>

    @Update
    fun update(options: Opciones)

    @Insert
    fun insert(options: Opciones)

    @Delete
    fun delete(options: Opciones)

    @Query("DELETE FROM options WHERE user = :id")
    fun deleteSpecific(id: String)

    @Query("SELECT * FROM options ORDER BY id desc limit 1")
    fun getMaxId() : List<Opciones>

    @Query("DELETE FROM options WHERE user != :id")
    fun deleteAllNotEqual(id: String)

    @Query("DELETE FROM options WHERE user = :id")
    fun deleteAll(id: String)

    @Query("UPDATE options SET user = :newName WHERE user = :name")
    fun updateUserOptions(name : String, newName : String)
}