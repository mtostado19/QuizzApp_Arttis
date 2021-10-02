package com.example.quizapp_arttis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class juego : AppCompatActivity() {
    private lateinit var txtCurrent: TextView
    private lateinit var txtClues: TextView
    private lateinit var lvAnswers: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        txtCurrent = findViewById(R.id.totalCurrent_text)
        txtClues = findViewById(R.id.clues_text)
        lvAnswers = findViewById(R.id.AnswersLV)

        txtCurrent.text = "1/10"
        txtClues.text = "Pistas: 2"

        var arrayAdapter : ArrayAdapter<*>
        val options = mutableListOf("1", "2", "3", "4")

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)
        lvAnswers.adapter = arrayAdapter
    }


}