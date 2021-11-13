package com.example.quizapp_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import android.widget.Toast
import androidx.room.Room
import com.example.room_demo_application.db.AppDatabase
import com.example.room_demo_application.db.Opciones
import com.google.gson.Gson


class OptionActivity : AppCompatActivity() {
    // Declarar variables
    private lateinit var sliderPreguntas: com.google.android.material.slider.Slider
    private lateinit var sliderDificultad: com.google.android.material.slider.Slider
    private lateinit var ckbTodo : CheckBox
    private lateinit var ckbGeo : CheckBox
    private lateinit var ckbPro : CheckBox
    private lateinit var ckbPeli : CheckBox
    private lateinit var ckbCiencia : CheckBox
    private lateinit var ckbHistoria : CheckBox
    private lateinit var ckbVideojuegos : CheckBox
    private lateinit var btnRandom : Button
    private lateinit var btnSave : Button
    private lateinit var swtichPistas : Switch
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        sliderPreguntas = findViewById(R.id.slider_preguntas)
        sliderDificultad = findViewById(R.id.sliderDificultad)
        ckbTodo = findViewById(R.id.ckb_todo)
        ckbGeo = findViewById(R.id.ckb_geografia)
        ckbPro = findViewById(R.id.ckb_programacion)
        ckbPeli = findViewById(R.id.ckb_peliculas)
        ckbCiencia = findViewById(R.id.ckb_ciencia)
        ckbHistoria = findViewById(R.id.ckb_historia)
        ckbVideojuegos = findViewById(R.id.ckb_videojuegos)
        btnRandom = findViewById(R.id.button)
        btnSave = findViewById(R.id.btnSave)
        swtichPistas = findViewById(R.id.switch2)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v3.db"
        ).allowMainThreadQueries().build()

        val optionsDB = db.optionsDAO()


        var QuestionGeografia = resources.getString(R.string.psGeografía)
        var QuestionVideojuegos = resources.getString(R.string.psVideojuegos)
        var QuestionHistoria = resources.getString(R.string.psHistoria)
        var QuestionCiencia = resources.getString(R.string.psCiencia)
        var QuestionPeliculas = resources.getString(R.string.psPeliculas)
        var QuestionProgramación = resources.getString(R.string.psProgramación)


        // Info para mandar a mainActivity
        var NumPreguntas = 6
        var Temas = mutableListOf<String>(QuestionGeografia,QuestionVideojuegos,QuestionHistoria,QuestionCiencia,QuestionPeliculas,QuestionProgramación)
        var dificultad = 1 // 1 : 2 - 2 : 3 - 3 : 4
        var pistas = false
        // Aqui termina la info que se manda a mainActivity

        val optionsExist = optionsDB.getSpecific("general")
        if (!optionsExist.isEmpty()) {
            NumPreguntas = optionsExist[0].numQuestions
            Temas = optionsExist[0].categories.split("^").toCollection(ArrayList())
            dificultad = optionsExist[0].difficulty
            pistas = optionsExist[0].clues
        }

        println(Temas)
        println(Temas.size)

        var CheckBoxValues = arrayListOf<CheckBox>(ckbGeo, ckbPro, ckbPeli, ckbCiencia, ckbHistoria, ckbVideojuegos)

        ckbTodo.isChecked = false // TODO: FALTA MANDAR LOS DATOS A MAIN ACTIVITY, VALIDAR QUE NO PUEDE SELECCIONAR MAS TEMAS QUE NUMERO DE PREGUNTAS
        ckbTodo.isEnabled = false

        sliderPreguntas.value= NumPreguntas.toFloat()
        sliderDificultad.value = dificultad.toFloat()
        swtichPistas.isChecked = pistas

        //for (x in 0..5){ // Este coso de aqui prende todos los checkbox
            //CheckBoxValues[x].isChecked = true
        //}

        for (check in Temas) {
            if (check==QuestionGeografia){
                ckbGeo.isChecked = true
            }
            if (check==QuestionProgramación){
                ckbPro.isChecked = true
            }
            if (check==QuestionPeliculas){
                ckbPeli.isChecked = true
            }
            if (check==QuestionCiencia){
                ckbCiencia.isChecked = true
            }
            if (check==QuestionHistoria){
                ckbHistoria.isChecked = true
            }
            if (check==QuestionVideojuegos){
                ckbVideojuegos.isChecked = true
            }
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
            var banderaSama = false
            for (x in 0..5){
                var NumRandomCheckboxMat = (0..1).random() // Al menos uno debe ser positivo
                if (NumRandomCheckboxMat == 1){
                    banderaSama = true
                }
                if (!banderaSama && x==5 ){
                    NumRandomCheckboxMat = 1
                }
                CheckBoxValues[x].isChecked = NumRandomCheckboxMat != 0
            }
            sliderPreguntas.value = (5..10).random().toFloat()
        }

        btnSave.setOnClickListener {
            NumPreguntas = sliderPreguntas.value.toInt()
            dificultad = sliderDificultad.value.toInt()
            pistas = swtichPistas.isChecked
            Temas.clear()

            if (ckbGeo.isChecked){
                Temas.add(QuestionGeografia)
            }
            if (ckbVideojuegos.isChecked){
                Temas.add(QuestionVideojuegos)
            }
            if (ckbCiencia.isChecked){
                Temas.add(QuestionCiencia)
            }
            if (ckbHistoria.isChecked){
                Temas.add(QuestionHistoria)
            }
            if (ckbPeli.isChecked){
                Temas.add(QuestionPeliculas)
            }
            if (ckbPro.isChecked){
                Temas.add(QuestionProgramación)
            }

            if (Temas.size == 6 && NumPreguntas == 5 || Temas.size==1 && NumPreguntas>5 ){ // falta validar que si es un tema no puede elegir mas de 5 preguntas
                Toast.makeText(this, resources.getString(R.string.errorMesageOptions), Toast.LENGTH_LONG).show()
            } else {

                var intent = Intent(this , MainActivity::class.java)
                val existingOptions = optionsDB.getSpecific("general")
                if (existingOptions.isEmpty()) {
                    optionsDB.insert(Opciones(0, dificultad, NumPreguntas, ArrayList(Temas).joinToString("^"), pistas, "general"))
                } else {
                    optionsDB.update(Opciones(0, dificultad, NumPreguntas, ArrayList(Temas).joinToString("^"), pistas, "general"))
                }

                startActivity(intent)
            }
        }

    }
}