package com.example.room_demo_application.db

import androidx.room.*

@Database(entities = [Puntuacion::class, Opciones::class], version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun scoreDAO(): PuntuacionDAO
    abstract fun optionsDAO(): OpcionesDAO
}