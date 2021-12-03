package com.example.room_demo_application.db

import androidx.room.*
import com.example.quizapp_arttis.db.Puntuacion
import com.example.quizapp_arttis.db.Usuario
import com.example.quizapp_arttis.db.UsuarioDAO

@Database(entities = [Puntuacion::class, Opciones::class, JuegoActual::class, Usuario::class], version = 4)
abstract class AppDatabase : RoomDatabase(){
    abstract fun scoreDAO(): PuntuacionDAO
    abstract fun optionsDAO(): OpcionesDAO
    abstract fun currenGameDAO(): JuegoActualDAO
    abstract fun usuarioDAO(): UsuarioDAO
}