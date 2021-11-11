package com.example.quizapp_arttis

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize


class GameModel(val questions: MutableList<Question>, val options: Options ) : ViewModel() {


    //var questions = mutableListOf<Question>()
    private var gson = Gson()

    private var extraClue: Int = 0
    private var clues: Int = 1
    private var currentIndex: Int = 0
    private var hintsUsed: Int = 0
   

    fun setTypes () {
        for (e in questions) {
            e.text = e.text.toString()
            e.right = e.right.toString()
            e.values = e.values.toList()
            e.answered = e.answered.toString()
            e.topic = e.topic.toString()
        }

        options.level = options.level.toInt()
        options.numberOfQuestions = options.numberOfQuestions.toInt()
    }

    fun getValues(): String {
        val returnValue = SavedData(extraClue, clues, currentIndex, hintsUsed)
        return gson.toJson(returnValue)
    }

    fun getMax() = options.numberOfQuestions

    fun setValues(dataString: String) {
        val data = gson.fromJson(dataString, SavedData::class.java)

        extraClue = data.extraClue
        clues = data.clues
        currentIndex = data.currentIndex
        hintsUsed = data.hintsUsed
    }
    
    fun getQuestionString() : String {
        return gson.toJson(questions)
    }

    fun getOptionsString1() : String {
        return gson.toJson(options)
    }

    fun getCurrentQuestion() = questions[currentIndex]

    fun getIndex() = currentIndex

    fun getClues() = clues

    fun getDifficulty() = options.level

    fun setClues(num: Int): Int {
        clues = num
        return clues
    }

    fun nextQuestion(): Question {
        currentIndex = (currentIndex + 1) % questions.size
        return questions[currentIndex]
    }

    fun prevQuestion(): Question {
        currentIndex = (currentIndex - 1) % questions.size
        if (currentIndex < 0) {
            currentIndex = questions.size - 1
        }
        return questions[currentIndex]
    }

    fun checkAnswer(value: String): Boolean {
        questions[currentIndex].answered = value;
        val result = value == questions[currentIndex].right
        if (result && !questions[currentIndex].hintUsed) {
            extraClue += 1
            if (extraClue == 2) {
                clues += 1
                extraClue = 0
            }
        }
        return result
    }

    fun useClue(): Boolean {
        if (options.hint) {
            if (clues > 0) {
                questions[currentIndex].hintUsed = true
                extraClue = 0
                return true
            } else {
                return false
            }
        } else {
            return false
        }

    }

    fun reduceClue() {
        hintsUsed += 1
        clues -= 1
    }

    fun secondClue(): Int {
        questions[currentIndex].answered = questions[currentIndex].right

        return questions[currentIndex].values.indexOf(questions[currentIndex].right)
    }

    fun getScore(): Array<Int>? {
        var cont = 0
        var right = 0
        var wrong = 0

        for (question in questions) {
            if (question.answered == "not") {
                return null
            } else {
                if (question.answered == question.right) {
                    right += 1
                    if (question.hintUsed) {
                        cont += 5
                    } else {
                        cont += 10
                    }
                } else {
                    wrong += 1
                }
            }
        }
        //cont * options.level
        return arrayOf(right, wrong, hintsUsed, cont * options.level)
    }
}
