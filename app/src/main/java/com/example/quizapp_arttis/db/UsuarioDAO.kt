package com.example.quizapp_arttis.db

import androidx.room.*
import com.example.room_demo_application.db.JuegoActual

@Dao
interface UsuarioDAO {
    @Query("Select * FROM users")
    fun getAll() : List<Usuario>

    @Query("SELECT * FROM users WHERE name = :name")
    fun getSpecific(name: String) : List<Usuario>

    @Query("SELECT * FROM users WHERE active = :active")
    fun getActiveUser(active: String) : List<Usuario>

    @Update
    fun update(user: Usuario)

    @Insert
    fun insert(user: Usuario)

    @Delete
    fun delete(user: Usuario)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteSpecific(id: String)

    @Query("SELECT * FROM users ORDER BY id desc limit 1")
    fun getMaxId() : List<Usuario>
}