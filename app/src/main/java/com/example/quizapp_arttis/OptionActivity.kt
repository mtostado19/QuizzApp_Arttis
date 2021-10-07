package com.example.quizapp_arttis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox



class OptionActivity : AppCompatActivity() {
    // Declarar variables
    private lateinit var sliderPreguntas: com.google.android.material.slider.Slider
    private lateinit var ckbTodo : CheckBox
    private lateinit var ckbGeo : CheckBox
    private lateinit var ckbPro : CheckBox
    private lateinit var ckbPeli : CheckBox
    private lateinit var ckbCiencia : CheckBox
    private lateinit var ckbHistoria : CheckBox
    private lateinit var ckbVideojuegos : CheckBox
    private lateinit var btnRandom : Button
    private lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        sliderPreguntas = findViewById(R.id.slider_preguntas)

        ckbTodo = findViewById(R.id.ckb_todo)
        ckbGeo = findViewById(R.id.ckb_geografia)
        ckbPro = findViewById(R.id.ckb_programacion)
        ckbPeli = findViewById(R.id.ckb_peliculas)
        ckbCiencia = findViewById(R.id.ckb_ciencia)
        ckbHistoria = findViewById(R.id.ckb_historia)
        ckbVideojuegos = findViewById(R.id.ckb_videojuegos)
        btnRandom = findViewById(R.id.button)
        btnSave = findViewById(R.id.btnSave)

        var CheckBoxValues = arrayListOf<CheckBox>(ckbGeo, ckbPro, ckbPeli, ckbCiencia, ckbHistoria, ckbVideojuegos)

        ckbTodo.isChecked = false // No olvidar bloquear el checkbox activo cuando es el unico
        ckbTodo.isEnabled = false

        for (x in 0..5){
            CheckBoxValues[x].isChecked = true
        }

        fun CheckTodoEnable() {
            var bandera = true
            for (x in 0..5){
                if (!CheckBoxValues[x].isChecked){
                    bandera = false
                }
            }
            if (bandera){
                ckbTodo.isChecked = false
            }
            ckbTodo.isEnabled = !bandera
        }

        fun OtakuPrietoKunOnichanSamaSensei() {
            var banderaOnichan = 0 // va a contar cuantos checkbox estan activados
            var checkloked = ckbGeo
            for (x in 0..5){
                if (CheckBoxValues[x].isChecked){
                    banderaOnichan += 1
                    checkloked = CheckBoxValues[x]
                }
            }
            if (banderaOnichan==1){
                checkloked.isEnabled = false
            }
            if (banderaOnichan>1){
                for (y in 0..5){
                    CheckBoxValues[y].isEnabled = true
                }
            }
        }

        ckbGeo.setOnClickListener {
            CheckTodoEnable()
            OtakuPrietoKunOnichanSamaSensei()
        }
        ckbPro.setOnClickListener {
            CheckTodoEnable()
            OtakuPrietoKunOnichanSamaSensei()
        }
        ckbPeli.setOnClickListener {
            CheckTodoEnable()
            OtakuPrietoKunOnichanSamaSensei()
        }
        ckbCiencia.setOnClickListener {
            CheckTodoEnable()
            OtakuPrietoKunOnichanSamaSensei()
        }
        ckbHistoria.setOnClickListener {
            CheckTodoEnable()
            OtakuPrietoKunOnichanSamaSensei()
        }
        ckbVideojuegos.setOnClickListener {
            CheckTodoEnable()
            OtakuPrietoKunOnichanSamaSensei()
        }


        ckbTodo.setOnClickListener {
            if (ckbTodo.isChecked){
                for (x in 0..5){
                    CheckBoxValues[x].isChecked = true
                }
            }
            CheckTodoEnable()
        }

        btnRandom.setOnClickListener {
            for (x in 0..5){
                val NumRandomCheckboxMat = (0..1).random() // Al menos uno debe ser positivo
                CheckBoxValues[x].isChecked = NumRandomCheckboxMat != 0
            }
            sliderPreguntas.value = (5..10).random().toFloat()
        }

        btnSave.setOnClickListener {

        }

    }
}