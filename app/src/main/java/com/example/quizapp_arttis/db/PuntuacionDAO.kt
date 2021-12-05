package com.example.room_demo_application.db

import androidx.room.*
import com.example.quizapp_arttis.db.Puntuacion
import com.example.quizapp_arttis.db.Usuario

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

    @Query("SELECT * FROM score ORDER BY date DESC, hints ASC LIMIT 5")
    fun getAscDate() : List<Puntuacion>

    @Query("SELECT id, date, SUM(score) as score , hints, user FROM score GROUP BY user ")
    fun getAscNumPlaying() : List<Puntuacion>

    @Query("SELECT id, date, SUM(score) as score , hints, user FROM score GROUP BY user ") //! Pa mañana
    fun getAscMatchesPlayed() : List<Puntuacion>


    @Update
    fun update(score: Puntuacion)

    @Insert
    fun insert(score: Puntuacion)

    @Delete
    fun delete(score: Puntuacion)

    @Query("DELETE FROM score WHERE user = :id")
    fun deleteSpecific(id: String)

    @Query("SELECT * FROM score ORDER BY id desc limit 1")
    fun getMaxId() : List<Puntuacion>

    @Query("DELETE FROM score WHERE user = :id")
    fun deleteAll(id: String)
}