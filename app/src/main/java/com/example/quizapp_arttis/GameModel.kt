package com.example.quizapp_arttis

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
class GameModel(var questionsString: String, var optionsString: String ) : ViewModel(),

    Parcelable {

    var gson = Gson()

    private var extraClue: Int = 0
    private var clues: Int = 1
    private var currentIndex: Int = 0
    private var hintsUsed: Int = 0

    val questionType = object : TypeToken<List<Question>>() {}.type

    // private var questions = gson.fromJson<MutableList<Question>>(questionStringD, Question::class.java)
    private var questions = gson.fromJson<List<Question>>(questionsString, questionType)
    private var options = gson.fromJson<Options>(optionsString, Options::class.java)

    fun getValues(): IntArray {
        val returnValue = intArrayOf(extraClue, clues, currentIndex, hintsUsed)
        return returnValue
    }

    fun setValues(data: IntArray) {
        extraClue = data[0]
        clues = data[1]
        currentIndex = data[2]
        hintsUsed = data[3]
    }

    fun getCurrentQuestion() = questions[currentIndex]

    fun getIndex() = currentIndex

    fun getClues() = clues

    fun setClues(num: Int) : Int  {
        clues = num
        return clues
    }

    fun nextQuestion() : Question {
        currentIndex = (currentIndex + 1) % questions.size
        return questions[currentIndex]
    }

    fun prevQuestion() : Question {
        currentIndex =  (currentIndex - 1) % questions.size
        if (currentIndex < 0) {
            currentIndex = questions.size - 1
        }
        return questions[currentIndex]
    }

    fun checkAnswer(value : String) : Boolean {
        questions[currentIndex].answered = value;
        val result = value === questions[currentIndex].right
        if (result) {
            extraClue += 1
            if (extraClue === 2) {
                clues += 1
                extraClue = 0
            }
        }
        return result
    }

    fun useClue(): Boolean {
        if (clues > 0) {
            hintsUsed += 1
            questions[currentIndex].hintUsed = true
            clues = clues - 1
            extraClue = 0
            return true
        } else {
            return false
        }
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
            if (question.answered === "not") {
                return null
            } else {
                if (question.answered === question.right){
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
        return arrayOf(right, wrong, hintsUsed,  cont * options.level)
    }

}