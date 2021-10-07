package com.example.quizapp_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnPlay: Button
    private lateinit var btnOption: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPlay = findViewById(R.id.play_button)
        btnOption = findViewById(R.id.btn_option)

        var QuestionList = mutableListOf<Question>()

        var chingatumadre = "¿Chingas a tu madre?*Si~No~Talvez~Simon|¿Tostado gay?*Si~No~Talvez~Simon"


        btnPlay.setOnClickListener { _ ->
            val intent = Intent(this, juego::class.java)
            // intent.putExtra("EXTRA_QUESTION_TEXT", gameModel.getCurrentQuestion().text)
            // startActivityForResult(intent, 5)
            startActivity(intent)
        }

        btnOption.setOnClickListener { _ ->
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }
    }
}