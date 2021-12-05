package com.example.quizapp_arttis

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.room_demo_application.db.AppDatabase
import com.example.room_demo_application.db.Opciones
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var btnPlay: Button
    private lateinit var btnOption: Button
    private lateinit var btnPuntacion: Button
    private lateinit var btnUser: ImageButton
    var NumPreguntas = 6
    var dificultad = 1
    var pistas = false

    private lateinit var Temas : ArrayList<String>
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPlay = findViewById(R.id.play_button)
        btnOption = findViewById(R.id.btn_option)
        btnPuntacion = findViewById(R.id.btn_puntuacion)
        btnUser = findViewById(R.id.user_button)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v4.db"
        ).allowMainThreadQueries().build()

        db.usuarioDAO()

        val currentGame = db.currenGameDAO()
        val optionsDB = db.optionsDAO()
        val deleteGson = Gson()
        val existingOptions = optionsDB.getSpecific("general")


        if (!existingOptions.isEmpty()) {
            NumPreguntas = existingOptions[0].numQuestions
            dificultad = existingOptions[0].difficulty
            pistas = existingOptions[0].clues
        }


        var QuestionList = mutableListOf<Question>()

        var QuestionGeografia = resources.getString(R.string.psGeografía)
        var QuestionVideojuegos = resources.getString(R.string.psVideojuegos)
        var QuestionHistoria = resources.getString(R.string.psHistoria)
        var QuestionCiencia = resources.getString(R.string.psCiencia)
        var QuestionPeliculas = resources.getString(R.string.psPeliculas)
        var QuestionProgramación = resources.getString(R.string.psProgramación)

        Temas = mutableListOf<String>(
            QuestionGeografia,
            QuestionVideojuegos,
            QuestionHistoria,
            QuestionCiencia,
            QuestionPeliculas,
            QuestionProgramación
        ).toCollection(ArrayList())

        val existingOPTS = optionsDB.getSpecific("general")
        Log.d("Optioooooons", deleteGson.toJson(existingOPTS))
        if (!existingOPTS.isEmpty()) {
            NumPreguntas = existingOPTS[0].numQuestions
            dificultad = existingOPTS[0].difficulty
            pistas = existingOPTS[0].clues
            Temas = existingOPTS[0].categories.split("^").toCollection(ArrayList())
        }

        Log.d("Optioooooons", deleteGson.toJson(Temas))
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
            Log.d("Optioooooons", "1")

            var questionsTxt = topicQuestion.split("|") // obtiene preguntas y respuestas
            var questionAnswers =
                questionsTxt.shuffled().map { it.split("*") } // separa las preuntas de las respuestas

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
            val activeGame = currentGame.getSpecific("general")
            if (!activeGame.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.resumeGame))
                builder.setMessage(resources.getString(R.string.resumeGameQuestion))
                builder.setNegativeButton(resources.getString(R.string.confirmResponse)) { dialogInterface: DialogInterface, i: Int ->
                    val intent = Intent(this, juego::class.java)
                    var QuestionString = activeGame[0].questions
                    var ObstionString = activeGame[0].options

                    intent.putExtra("Preguntas", QuestionString)
                    intent.putExtra("options", ObstionString)
                    startActivity(intent)
                }
                builder.setPositiveButton(resources.getString(R.string.RejectRespone)) { dialogInterface: DialogInterface, i: Int ->
                    currentGame.deleteSpecific("general")
                    val intent = Intent(this, juego::class.java)

                    val gson = Gson()
                    QuestionList.shuffle()
                    val QuestionString = gson.toJson(QuestionList)
                    val newestOptions = Options(existingOptions[0].difficulty, existingOptions[0].numQuestions, existingOptions[0].clues)
                    val ObstionString = gson.toJson(newestOptions)
                    intent.putExtra("Preguntas", QuestionString)
                    intent.putExtra("options", ObstionString)
                    startActivity(intent)
                }

                builder.create().show()
            } else {
                val intent = Intent(this, juego::class.java)
                QuestionList.shuffle()
                val gson = Gson()
                val QuestionString = gson.toJson(QuestionList)
                val ObstionString = gson.toJson(optionObj)
                intent.putExtra("Preguntas", QuestionString)
                intent.putExtra("options", ObstionString)
                startActivity(intent)
            }
        }

        btnOption.setOnClickListener { _ ->
            val intent = Intent(this, OptionActivity::class.java)
            val optExist = optionsDB.getSpecific("general")
            if (optExist.isEmpty()) {
                optionsDB.insert(Opciones(0, dificultad, NumPreguntas, Temas.joinToString("^"), pistas, "general"))
            } else {
                optionsDB.update(Opciones(0, dificultad, NumPreguntas, Temas.joinToString("^"), pistas, "general"))
            }
            startActivity(intent)
        }

        btnPuntacion.setOnClickListener { _ ->
            val intent = Intent(this, PointsActivity::class.java)
            startActivity(intent)
        }

        btnUser.setOnClickListener { _ ->
            val intent = Intent(this, activity_user::class.java)
            startActivity(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val optionsDB = db.optionsDAO()
        val existingOptions = optionsDB.getSpecific("general")
        if (existingOptions.isEmpty()) {
            optionsDB.insert(Opciones(0, dificultad, NumPreguntas, Temas.joinToString("^"), pistas, "general"))
        } else {
            optionsDB.update(Opciones(0, dificultad, NumPreguntas, Temas.joinToString("^"), pistas, "general"))
        }
    }

}