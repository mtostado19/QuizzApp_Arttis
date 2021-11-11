package com.example.quizapp_arttis

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.room_demo_application.db.AppDatabase
import com.example.room_demo_application.db.JuegoActual
import com.example.room_demo_application.db.Puntuacion
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class juego : AppCompatActivity()  {
    private lateinit var txtQuestion: TextView
    private lateinit var txtCurrent: TextView
    private lateinit var txtClues: TextView
    private lateinit var lvAnswers: ListView
    private lateinit var btnNext: Button
    private lateinit var btnPrev: Button
    private lateinit var btnClue: ImageButton

    private lateinit var txtRight: TextView
    private lateinit var txtWrong: TextView
    private lateinit var txtCluesScore: TextView
    private lateinit var txtTotal: TextView
    private lateinit var scoreImage: ImageView
    private lateinit var questionString : String
    private lateinit var optionsString : String
    private lateinit var valuesString : String

    private lateinit var gameModel: GameModel
    private  lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v3.db"
        ).allowMainThreadQueries().build()

        var gson = Gson()
        val currentGame = db.currenGameDAO()
        val scoreSetter = db.scoreDAO()
        val currentGameValues = currentGame.getSpecific("general")
        if (!currentGameValues.isEmpty()) {
            questionString = currentGameValues[0].questions
            optionsString = currentGameValues[0].options
            valuesString = currentGameValues[0].gameValues
        } else {
            questionString = intent.getStringExtra("Preguntas").toString()
            optionsString = intent.getStringExtra("options").toString()
        }
        Log.d("Optiooooons", optionsString )
        Log.d("Questiooooons", questionString )

        var string1 = questionString.split("[{")[1].split("}]")[0].split("}")

        var finalString = mutableListOf<String>()

        for (i in 0 .. string1.size - 1) {
            if (i == 0) {
                finalString.add("{" + string1[i] + "}")
            } else {
                finalString.add(string1[i].drop(1) + "}")
            }

        }

        var optionsToSend:Options = gson.fromJson(optionsString, Options::class.java)

        var questionsToSend = mutableListOf<Question>()
        for (e in finalString) {
            questionsToSend.add(gson.fromJson(e, Question::class.java))

        }


        gameModel = GameModel(questionsToSend, optionsToSend)
        gameModel.setTypes()
        lvAnswers = findViewById(R.id.AnswersLV)
        txtQuestion = findViewById(R.id.question_text)
        txtCurrent = findViewById(R.id.totalCurrent_text)
        txtClues = findViewById(R.id.clues_text)
        btnNext = findViewById(R.id.next_button)
        btnPrev = findViewById(R.id.prev_button)
        btnClue = findViewById<ImageButton>(R.id.clue_button)

        Log.d("Valueeees", gson.toJson(scoreSetter.getTopFive()))

        var arrayAdapter : ArrayAdapter<String>
        var lvData = gameModel.getCurrentQuestion().values.toMutableList()

        // arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lvData)
        arrayAdapter = customAdapter(this, lvData.toCollection(ArrayList()))
        lvAnswers.adapter = arrayAdapter

        if (!optionsToSend.hint) {
            btnClue.setImageResource(R.drawable.question_cancelled)
        }


        if (!currentGameValues.isEmpty()) {
//            savedInstanceState.getIntArray("KEY_GAME_MODEL_Values")?.let { gameModel.setValues(it) }
            gameModel.setValues(valuesString)
            val currentQuestion = gameModel.getCurrentQuestion()
            arrayAdapter.clear()
            arrayAdapter.addAll(currentQuestion.values.toCollection(ArrayList()))

            if (currentQuestion.answered != "not") {
                lvAnswers.setEnabled(false)
                val index = currentQuestion.values.indexOf(currentQuestion.answered);
                val stringColor = if(currentQuestion.values[index] == currentQuestion.right) Color.parseColor("#05ff15") else Color.parseColor("#ff0000")
                Log.d("A VER QUE U", "Index: ${index}, String: ${stringColor}, children: ${lvAnswers.childCount}")
                lvAnswers.getChildAt(index)?.setBackgroundColor(stringColor)
            } else {
                lvAnswers.setEnabled(true)
            }
        }

        var pistas : Int
        if (!optionsToSend.hint) {
            pistas = 0
        } else {
            pistas = gameModel.getClues()
        }
        txtQuestion.text = gameModel.getCurrentQuestion().text
        txtCurrent.text = "${gameModel.getIndex() + 1}/${gameModel.getMax()}"
        txtClues.text = "${resources.getString(R.string.clue)} ${pistas}"

        var buildScore = AlertDialog.Builder(this)
        buildScore.setTitle(resources.getString(R.string.completed))

        buildScore.setNegativeButton(resources.getString(R.string.close), { dialogInterface: DialogInterface, i: Int ->finish()})
        buildScore.setPositiveButton(resources.getString(R.string.stay), { dialogInterface: DialogInterface, i: Int -> })


        var dialogView = layoutInflater.inflate(R.layout.score_dialog, null)
        txtRight = dialogView.findViewById(R.id.right_text)
        txtWrong = dialogView.findViewById(R.id.wrong_text)
        txtCluesScore = dialogView.findViewById(R.id.clues_text)
        txtTotal = dialogView.findViewById(R.id.totalScore_text)
        scoreImage = dialogView.findViewById(R.id.score_image)
        buildScore.setView(dialogView)



        btnNext.setOnClickListener { _ ->
            if (!optionsToSend.hint) {
                pistas = 0
            } else {
                pistas = gameModel.getClues()
            }
            txtClues.text = "${resources.getString(R.string.clue)} ${pistas}"
            txtQuestion.text = gameModel.nextQuestion().text
            txtCurrent.text = "${gameModel.getIndex() + 1}/${gameModel.getMax()}"
            val currentQuestion =gameModel.getCurrentQuestion()
            lvData = currentQuestion.values.toMutableList()
            Log.d("CurrentAnswer", currentQuestion.right)
            arrayAdapter.clear()
            arrayAdapter.addAll(currentQuestion.values.toCollection(ArrayList()))

            for (i in 0..gameModel.getDifficulty()) {
                lvAnswers.getChildAt(i)?.setBackgroundColor(Color.parseColor("#040232"))
            }
            if (currentQuestion.answered != "not") {
                lvAnswers.setEnabled(false)
                val index = currentQuestion.values.indexOf(currentQuestion.answered)
                Log.d("A VER1", index.toString())
                val stringColor = if(currentQuestion.values[index] == currentQuestion.right) Color.parseColor("#05ff15") else Color.parseColor("#ff0000")
                lvAnswers.getChildAt(index)?.setBackgroundColor(stringColor)
            } else {
                lvAnswers.setEnabled(true)
            }

        }

        btnPrev.setOnClickListener { _ ->
            if (!optionsToSend.hint) {
                pistas = 0
            } else {
                pistas = gameModel.getClues()
            }
            Log.d("Paso 1", "Prueba: ${lvAnswers.childCount}")
            txtClues.text = "${resources.getString(R.string.clue)} ${pistas}"
            txtQuestion.text = gameModel.prevQuestion().text
            txtCurrent.text = "${gameModel.getIndex() + 1}/${gameModel.getMax()}"

            val currentQuestion = gameModel.getCurrentQuestion()
            lvData = currentQuestion.values.toMutableList()
            Log.d("CurrentAnswer", currentQuestion.right)
            arrayAdapter.clear()
            arrayAdapter.addAll(currentQuestion.values.toCollection(ArrayList()))

            for (i in 0..gameModel.getDifficulty()) {
                lvAnswers.getChildAt(i)?.setBackgroundColor(Color.parseColor("#040232"))
            }

            if (currentQuestion.answered != "not") {
                Log.d("A VER0", "1: ${currentQuestion.answered} ...  2: ${currentQuestion.values.toString()}")
                val position = currentQuestion.values.indexOf(currentQuestion.answered)
                Log.d("A VER1", position.toString())
                val stringColor = if(currentQuestion.values[position] == currentQuestion.right) Color.parseColor("#05ff15") else Color.parseColor("#ff0000")

                lvAnswers.getChildAt(position)?.setBackgroundColor(stringColor)
                //ListView.Adapter.GetView(position, null, lvAnswers)?.setBackgroundColor(stringColor)
                lvAnswers.setEnabled(false)
            } else {
                lvAnswers.setEnabled(true)
            }

        }

         lvAnswers.setOnItemClickListener { parent, view, position, id ->
             Log.d("QUE ES:", lvData[position])
             val result = gameModel.checkAnswer(lvData[position])
             val stringColor = if(result) Color.parseColor("#05ff15") else Color.parseColor("#ff0000")
             lvAnswers.getChildAt(position).setBackgroundColor(stringColor)
             lvAnswers.setEnabled(false)
             val score = gameModel.getScore()

             if (score != null) {
                 Log.d("HOLA", score.toString())
                 txtRight.text = "${resources.getString(R.string.right)} ${score[0]}"
                 txtWrong.text = "${resources.getString(R.string.wrong)} ${score[1]}"
                 txtCluesScore.text = "${resources.getString(R.string.clue)} ${score[2]}"
                 txtTotal.text = "${resources.getString(R.string.score)} ${score[3]}"
                 if (score[0] > (optionsToSend.numberOfQuestions / 2) ) {
                     scoreImage.setImageResource(R.drawable.star_eyes)
                 } else {
                     scoreImage.setImageResource(R.drawable.clown)
                 }
                 buildScore.create()
                 buildScore.show()

                 val currentDay = "${Calendar.getInstance().get(Calendar.YEAR)}-${Calendar.getInstance().get(Calendar.MONTH) + 1}-${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)}"
                 scoreSetter.insert(Puntuacion(scoreSetter.getAll().size, currentDay, score[3], score[2], "general"))
                 currentGame.deleteSpecific("general")
                 // Toast.makeText(this, score.toString(), Toast.LENGTH_LONG).show()
             }

        }

        btnClue.setOnClickListener{ _->
            var currentQuestion = gameModel.getCurrentQuestion()
            if (currentQuestion.answered != "not" && !currentQuestion.hintUsed) {
                return@setOnClickListener
            }

            if ((currentQuestion.hintUsed && gameModel.useClue()) || (gameModel.useClue() && gameModel.getDifficulty() == 1)) {
                lvAnswers.getChildAt(gameModel.secondClue()).setBackgroundColor(Color.parseColor("#05ff15"))
                gameModel.reduceClue()
                lvAnswers.setEnabled(false)
                txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"

                val score = gameModel.getScore()

                if (score != null) {
                    Log.d("HOLA", score.toString())
                    txtRight.text = "${resources.getString(R.string.right)} ${score[0]}"
                    txtWrong.text = "${resources.getString(R.string.wrong)} ${score[1]}"
                    txtCluesScore.text = "${resources.getString(R.string.clue)} ${score[2]}"
                    txtTotal.text = "${resources.getString(R.string.score)} ${score[3]}"
                    if (score[0] > (optionsToSend.numberOfQuestions / 2) ) {
                        scoreImage.setImageResource(R.drawable.star_eyes)
                    } else {
                        scoreImage.setImageResource(R.drawable.clown)
                    }
                    buildScore.create()
                    buildScore.show()
                    val currentDay = "${Calendar.getInstance().get(Calendar.YEAR)}-${Calendar.getInstance().get(Calendar.MONTH) + 1}-${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)}"
                    scoreSetter.insert(Puntuacion(scoreSetter.getAll().size, currentDay, score[3], score[2], "general"))
                    currentGame.deleteSpecific("general")
                    // Toast.makeText(this, score.toString(), Toast.LENGTH_LONG).show()
                }
                return@setOnClickListener
            }
            Log.d("Clueeees", gameModel.getClues().toString())
            if (gameModel.useClue()) {
                var index = currentQuestion.values.indexOf(currentQuestion.right)
                gameModel.reduceClue()

                val toDelete = getRandomValues(index, gameModel.getDifficulty());
                Log.d("A ver", toDelete.toString())

                for (i in toDelete) {
                    lvAnswers.getChildAt(i)?.setBackgroundColor(Color.parseColor("#ff0000"))
                }

                txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("QUIZZAPP_DEBUG", "onSaveInstanceState")

        val currentGame = db.currenGameDAO()
        if (currentGame.getSpecific("general").isEmpty()) {
            val gameLength = currentGame.getAll().size
            currentGame.insert(JuegoActual(gameLength, gameModel.getQuestionString(), gameModel.getOptionsString1(), gameModel.getValues(), "general"))
        } else {
            val gameOcurring = currentGame.getSpecific("general")[0]
            currentGame.update(JuegoActual(gameOcurring.id,gameModel.getQuestionString(), gameModel.getOptionsString1(), gameModel.getValues(), "general") )
        }
    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close the game?")
        builder.setNegativeButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            val currentGame = db.currenGameDAO()
            if (currentGame.getSpecific("general").isEmpty()) {
                val gameLength = currentGame.getAll().size
                currentGame.insert(JuegoActual(gameLength, gameModel.getQuestionString(), gameModel.getOptionsString1(), gameModel.getValues(), "general"))
            } else {
                val gameOcurring = currentGame.getSpecific("general")[0]
                currentGame.update(JuegoActual(gameOcurring.id,gameModel.getQuestionString(), gameModel.getOptionsString1(), gameModel.getValues(), "general") )
            }
            finish()
        }
        builder.setPositiveButton("No", { dialogInterface: DialogInterface, i: Int -> })

        builder.create().show()
    }


}

fun getRandomValues(forbidden : Int, max: Int ) : List<Int> {
    val result = mutableListOf<Int>()

    while (result.size < 2) {
        val random = (0..max).random()

        if(forbidden != random && !result.contains(random)){
            result.add(random)
        }
    }
    return result
}
