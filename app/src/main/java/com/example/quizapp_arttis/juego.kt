package com.example.quizapp_arttis

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

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

    private lateinit var gameModel: GameModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        var questions = mutableListOf<Question>(
            Question("Que es el queso?", "yes", listOf<String>("1", "3", "yes", "4"), "not", "Geography", false),
            Question("Que es el queso2?", "4", listOf<String>("1", "3", "No", "4"), "not", "Geography", false),
            Question("Que es el queso3?", "Maybe", listOf<String>("1", "3", "Maybe", "4"), "not", "Geography", false),
            Question("Que es el queso4?", "3", listOf<String>("1", "3", "IDK", "4"), "not", "Geography", false),
            Question("Que es el queso5?", "1", listOf<String>("1", "3", "Cheese", "4"), "not", "Geography", false),
          )

        var options = Options(3, 5, false)

        var gson = Gson()

        if (savedInstanceState != null) {
            questionString = savedInstanceState.getString("KEY_GAME_MODEL_QUESTIONS").toString()
            optionsString = savedInstanceState.getString("KEY_GAME_MODEL_OPTIONS").toString()
        } else {
            questionString = gson.toJson(questions)
            optionsString = gson.toJson(options)
        }


        var string1 = questionString.split("[{")[1].split("}]")[0].split("}")

        var finalString = mutableListOf<String>()

        for (i in 0 .. string1.size - 1) {
            if (i == 0) {
                finalString.add("{" + string1[i] + "}")
            } else {
                finalString.add(string1[i].drop(1) + "}")
            }
        }

        var questionsToSend = mutableListOf<Question>()
        for (e in finalString) {
            questionsToSend.add(gson.fromJson(e, Question::class.java))
        }

        var optionsToSend:Options = gson.fromJson(optionsString, Options::class.java)


        gameModel = GameModel(questionsToSend, optionsToSend)
        gameModel.setTypes()
        lvAnswers = findViewById(R.id.AnswersLV)
        txtQuestion = findViewById(R.id.question_text)
        txtCurrent = findViewById(R.id.totalCurrent_text)
        txtClues = findViewById(R.id.clues_text)
        btnNext = findViewById(R.id.next_button)
        btnPrev = findViewById(R.id.prev_button)
        btnClue = findViewById<ImageButton>(R.id.clue_button)

        var arrayAdapter : ArrayAdapter<String>
        val lvData = gameModel.getCurrentQuestion().values.toMutableList()

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lvData)
        lvAnswers.adapter = arrayAdapter

        if (savedInstanceState != null) {
            savedInstanceState.getIntArray("KEY_GAME_MODEL_Values")?.let { gameModel.setValues(it) }

            val currentQuestion = gameModel.getCurrentQuestion()
            arrayAdapter.clear()
            arrayAdapter.addAll(currentQuestion.values)

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

        txtQuestion.text = gameModel.getCurrentQuestion().text
        txtCurrent.text = "${gameModel.getIndex() + 1}/${options.numberOfQuestions}"
        txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close the game?")
        builder.setNegativeButton("Yes", { dialogInterface: DialogInterface, i: Int ->
            finish()
        })
        builder.setPositiveButton("No", { dialogInterface: DialogInterface, i: Int -> })

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
            Log.d("Paso 1", "aaa")
            txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"
            txtQuestion.text = gameModel.nextQuestion().text
            txtCurrent.text = "${gameModel.getIndex() + 1}/${options.numberOfQuestions}"
            val currentQuestion =gameModel.getCurrentQuestion()
            arrayAdapter.clear()
            arrayAdapter.addAll(currentQuestion.values)

            for (i in mutableListOf<Int>(0, 1, 2, 3)) {
                lvAnswers.getChildAt(i)?.setBackgroundColor(Color.parseColor("#040232"))
            }
            Log.d("A VER:", "${currentQuestion.hintUsed.javaClass.kotlin}")
            if (currentQuestion.answered != "not") {
                lvAnswers.setEnabled(false)
                val index = currentQuestion.values.indexOf(currentQuestion.answered);
                Log.d("QUE ES", "Index: ${index}, Lookin:${currentQuestion.answered} SIZE: ${currentQuestion.values.toString()}")
                val stringColor = if(currentQuestion.values[index] == currentQuestion.right) Color.parseColor("#05ff15") else Color.parseColor("#ff0000")
                lvAnswers.getChildAt(index)?.setBackgroundColor(stringColor)
            } else {
                lvAnswers.setEnabled(true)
            }

        }

        btnPrev.setOnClickListener { _ ->
            Log.d("Paso 1", "Prueba: ${lvAnswers.childCount}")
            txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"
            txtQuestion.text = gameModel.prevQuestion().text
            txtCurrent.text = "${gameModel.getIndex() + 1}/${options.numberOfQuestions}"

            val currentQuestion = gameModel.getCurrentQuestion()
            arrayAdapter.clear()
            arrayAdapter.addAll(currentQuestion.values)

            for (i in mutableListOf<Int>(0, 1, 2, 3)) {
                lvAnswers.getChildAt(i)?.setBackgroundColor(Color.parseColor("#040232"))
            }

            if (currentQuestion.answered != "not") {
                val position = currentQuestion.values.indexOf(currentQuestion.answered)
                //Log.d("A VER1", index.toString())
                val stringColor = if(currentQuestion.values[position] == currentQuestion.right) Color.parseColor("#05ff15") else Color.parseColor("#ff0000")

                lvAnswers.getChildAt(position)?.setBackgroundColor(stringColor)
                //ListView.Adapter.GetView(position, null, lvAnswers)?.setBackgroundColor(stringColor)
                lvAnswers.setEnabled(false)
            } else {
                lvAnswers.setEnabled(true)
            }

        }

         lvAnswers.setOnItemClickListener { parent, view, position, id ->
             Log.d("HOLA", "Hola: $position, $id, ${lvData[position]}")
             Log.d("HOLADOOOS", "Hola: ${gameModel.getCurrentQuestion().toString()}")
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
                 if (score[0] > 5 ) {
                     scoreImage.setImageResource(R.drawable.star_eyes)
                 } else {
                     scoreImage.setImageResource(R.drawable.clown)
                 }
                 buildScore.create()
                 buildScore.show()
                 // Toast.makeText(this, score.toString(), Toast.LENGTH_LONG).show()
             }

        }

        btnClue.setOnClickListener{ _->
            var currentQuestion = gameModel.getCurrentQuestion()
            if (currentQuestion.answered != "not" && !currentQuestion.hintUsed) {
                return@setOnClickListener
            }
            if (currentQuestion.hintUsed && gameModel.useClue()) {
                lvAnswers.getChildAt(gameModel.secondClue()).setBackgroundColor(Color.parseColor("#05ff15"))
                lvAnswers.setEnabled(false)
                txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"
                return@setOnClickListener
            }
            if (gameModel.useClue()) {
                var index = currentQuestion.values.indexOf(currentQuestion.right)

                val toDelete = getRandomValues(index);
                Log.d("A ver", toDelete.toString())

                for (i in toDelete) {
                    lvAnswers.getChildAt(i)?.setBackgroundColor(Color.parseColor("#ff0000"))
                }

            }
            txtClues.text = "${resources.getString(R.string.clue)} ${gameModel.getClues()}"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("QUIZZAPP_DEBUG", "onSaveInstanceState")
        outState.putIntArray("KEY_GAME_MODEL_Values", gameModel.getValues())
        outState.putString("KEY_GAME_MODEL_QUESTIONS", gameModel.getQuestionString())
        outState.putString("KEY_GAME_MODEL_OPTIONS", gameModel.getOptionsString1())

    }


}

fun getRandomValues(forbidden : Int ) : List<Int> {
    val result = mutableListOf<Int>()

    while (result.size < 2) {
        val random = (0..3).random()

        if(forbidden != random && !result.contains(random)){
            result.add(random)
        }
    }
    return result
}
