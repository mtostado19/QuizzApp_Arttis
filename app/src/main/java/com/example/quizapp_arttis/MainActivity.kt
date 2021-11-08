package com.example.quizapp_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.room.Room
import com.example.room_demo_application.db.AppDatabase
import com.example.room_demo_application.db.Opciones
import com.example.room_demo_application.db.Puntuacion
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var btnPlay: Button
    private lateinit var btnOption: Button
    var NumPreguntas = 6
    var dificultad = 1 // 1 : 2 - 2 : 3 - 3 : 4
    var pistas = false
    private  lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPlay = findViewById(R.id.play_button)
        btnOption = findViewById(R.id.btn_option)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v2.db"
        ).allowMainThreadQueries().build()

        val optionsDao = db.optionsDAO()
        var deleteGSON = Gson()
//         optionsDao.insert(Opciones(0,1, 1, "asdaw", true, "delete" ))
         optionsDao.deleteSpecific("delete")

        Log.d("HEREEEEE", deleteGSON.toJson(optionsDao.getAll()))

        var QuestionList = mutableListOf<Question>()

        var QuestionGeografia = resources.getString(R.string.psGeografía)
        var QuestionVideojuegos = resources.getString(R.string.psVideojuegos)
        var QuestionHistoria = resources.getString(R.string.psHistoria)
        var QuestionCiencia = resources.getString(R.string.psCiencia)
        var QuestionPeliculas = resources.getString(R.string.psPeliculas)
        var QuestionProgramación = resources.getString(R.string.psProgramación)

        var Temas = mutableListOf<String>(
            QuestionGeografia,
            QuestionVideojuegos,
            QuestionHistoria,
            QuestionCiencia,
            QuestionPeliculas,
            QuestionProgramación
        )

        if (savedInstanceState != null) {
            NumPreguntas = savedInstanceState.getInt("NumPreguntas")
            dificultad = savedInstanceState.getInt("dificultad")
            pistas = savedInstanceState.getBoolean("pistas")
        }

        if (intent != null) {
            NumPreguntas = intent.getIntExtra("NumPreguntas", 6)
            dificultad = intent.getIntExtra("dificultad", 1)
            pistas = intent.getBooleanExtra("pistas", false)
            Temas =
                intent.getStringArrayListExtra("Temas")?.toMutableList() ?: mutableListOf<String>(
                    QuestionGeografia,
                    QuestionVideojuegos,
                    QuestionHistoria,
                    QuestionCiencia,
                    QuestionPeliculas,
                    QuestionProgramación
                )
        }


        // ayudas de control de prguntas
        var banderaOtaku = false
        var tostazzz = NumPreguntas / Temas.size // Casi Par
        var intervalodecomida =
            tostazzz * Temas.size // este da el numero de preguntas que calcula inicialmente por ejemplo 3 temas 5 preguntas al dividir da 1 de cada uno, por ello calculamos cuanstas faltan para llegar a numero de preguntas
        var numeroFaltantes =
            NumPreguntas - intervalodecomida // ejemplo: si numero de preguntas es 5 y tostatazzz es 3 entonces me devuelve 2

        if (NumPreguntas % Temas.size == 0) {
        } else {
            banderaOtaku = true
        }


        for (topicQuestion in Temas) {

            var questionsTxt = topicQuestion.split("|") // obtiene preguntas y respuestas
            var questionAnswers =
                questionsTxt.map { it.split("*") } // separa las preuntas de las respuestas

            var questionResult =
                mutableListOf<List<List<String>>>() // crea listas para guardar el conjunto de lista de pregunta y listas de respuestan en el mismo conjunto

            for (answ in questionAnswers) {
                questionResult.add(listOf(listOf(answ[0]), answ[1].split("~")))
            }

            if (topicQuestion == QuestionGeografia) {
                if (banderaOtaku) {
                    for (qts in 0..tostazzz - 1 + numeroFaltantes) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Geografia",
                                false
                            )
                        )
                    }
                    banderaOtaku = false
                } else {
                    for (qts in 0..tostazzz - 1) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Geografia",
                                false
                            )
                        )
                    }
                }
            }

            if (topicQuestion == QuestionVideojuegos) {
                if (banderaOtaku) {
                    for (qts in 0..tostazzz - 1 + numeroFaltantes) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Videojuegos",
                                false
                            )
                        )
                    }
                    banderaOtaku = false
                } else {
                    for (qts in 0..tostazzz - 1) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Videojuegos",
                                false
                            )
                        )
                    }
                }
            }

            if (topicQuestion == QuestionHistoria) {
                if (banderaOtaku) {
                    for (qts in 0..tostazzz - 1 + numeroFaltantes) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Historia",
                                false
                            )
                        )
                    }
                    banderaOtaku = false
                } else {
                    for (qts in 0..tostazzz - 1) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Historia",
                                false
                            )
                        )
                    }
                }
            }

            if (topicQuestion == QuestionCiencia) {
                if (banderaOtaku) {
                    for (qts in 0..tostazzz - 1 + numeroFaltantes) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Ciencia",
                                false
                            )
                        )
                    }
                    banderaOtaku = false
                } else {
                    for (qts in 0..tostazzz - 1) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Ciencia",
                                false
                            )
                        )
                    }
                }
            }

            if (topicQuestion == QuestionPeliculas) {
                if (banderaOtaku) {
                    for (qts in 0..tostazzz - 1 + numeroFaltantes) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Peliculas",
                                false
                            )
                        )
                    }
                    banderaOtaku = false
                } else {
                    for (qts in 0..tostazzz - 1) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Peliculas",
                                false
                            )
                        )
                    }
                }
            }

            if (topicQuestion == QuestionProgramación) {
                if (banderaOtaku) {
                    for (qts in 0..tostazzz - 1 + numeroFaltantes) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Programacion",
                                false
                            )
                        )
                    }
                    banderaOtaku = false
                } else {
                    for (qts in 0..tostazzz - 1) {
                        var limitAnswerd = questionResult[qts][1].take(dificultad + 1)
                        QuestionList.add(
                            Question(
                                questionResult[qts][0][0],
                                questionResult[qts][1][0],
                                limitAnswerd.shuffled(),
                                "not",
                                "Programacion",
                                false
                            )
                        )
                    }
                }
            }


        }

        println("---------------")
        println(QuestionList)
        println(QuestionList.size)
        println("---------------")

        var optionObj = Options(dificultad, NumPreguntas, pistas)

        btnPlay.setOnClickListener { _ ->
            val intent = Intent(this, juego::class.java)
            QuestionList.shuffle()
            var gson = Gson()
            var QuestionString = gson.toJson(QuestionList)
            var ObstionString = gson.toJson(optionObj)
            intent.putExtra("Preguntas", QuestionString)
            intent.putExtra("options", ObstionString)
            startActivity(intent)
        }

        btnOption.setOnClickListener { _ ->
            val intent = Intent(this, OptionActivity::class.java)
            intent.putExtra("NumPreguntas", NumPreguntas)
            intent.putExtra("dificultad", dificultad)
            intent.putExtra("pistas", pistas)
            intent.putStringArrayListExtra("Temas", ArrayList(Temas))
            startActivity(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("NumPreguntas",NumPreguntas)
        outState.putInt("dificultad",dificultad)
        outState.putBoolean("pistas", pistas)
    }

}