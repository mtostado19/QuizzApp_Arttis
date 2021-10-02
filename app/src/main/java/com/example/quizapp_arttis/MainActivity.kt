package com.example.quizapp_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnPlay: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.play_button)

        btnPlay.setOnClickListener { _ ->
            val intent = Intent(this, juego::class.java)
            // intent.putExtra("EXTRA_QUESTION_TEXT", gameModel.getCurrentQuestion().text)
            // startActivityForResult(intent, 5)
            startActivity(intent)
        }
    }
}