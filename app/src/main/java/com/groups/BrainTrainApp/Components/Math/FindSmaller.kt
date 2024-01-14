package com.groups.BrainTrainApp.Components.Math

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.handleEndGame
import kotlin.math.roundToInt
import kotlin.random.Random

class FindSmaller : AppCompatActivity() {
    private lateinit var stageTxt: TextView
    private lateinit var scoreTxt: TextView
    private lateinit var btnBack: AppCompatButton
    private lateinit var progressBar: ProgressBar
    private lateinit var cb1: CheckBox
    private lateinit var cb2: CheckBox
    private var cbList = mutableListOf<CheckBox>()
    private lateinit var timer: Timer
    private val countDownTime = 20 //second
    private val clockTime = (countDownTime*1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    var totalPlayTime: Int = 0
    private var option1 = 0
    private var option2 = 0
    private var optionList = mutableListOf<Triple<Boolean, Int, String>>()
    private var operationList = mutableListOf<String>(" + ", " - ", " x ", " : ")
    private var numOperation1 = 0
    private var numOperation2 = 0
    private var stage = 1
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_find_smaller)
        btnBack = findViewById(R.id.button_back)
        btnBack.setOnClickListener{
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.MATH.toString())
            startActivity(intent)
        }
        progressBar = findViewById(R.id.progress_bar)
        stageTxt = findViewById(R.id.question_number)
        scoreTxt = findViewById(R.id.score_txt)
        cb1 = findViewById(R.id.checkbox1)
        cbList.add(cb1)
        cb2 = findViewById(R.id.checkbox2)
        cbList.add(cb2)
        handleClicked(cbList)
        timer = object : Timer(clockTime, 1000) {}
        resetGame()
    }

    override fun onPause() {
        super.onPause()
        timer.pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        timer.resumeTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.destroyTimer()
    }

    private fun resetGame(){
        stageTxt.setText("Câu hỏi: $stage")
        scoreTxt.setText("Điểm số: $score")
        setupTimer()
        generateOptions()
    }

    private fun setupTimer() {
        var secondLeft = 0
        timer.onTick = {millisUntilFinished ->
            val second = (millisUntilFinished / 1000.0f).roundToInt()
            if (second != secondLeft) {
                secondLeft = second
                totalPlayTime++
                Log.i("totalPlayTime", totalPlayTime.toString())
                progressBar.progress = secondLeft
            }
        }
        timer.onFinish = {
            handleTimeUp()
        }
        progressBar.max = progressTime.toInt()
        progressBar.progress = progressTime.toInt()
        timer.startTimer()
    }

    private fun handleTimeUp() {
        //TODO handle lose
        handleEndGame(this, score, totalPlayTime)
    }

    private fun generateOptions(){
        var smaller = true
        var ex1 = ""
        var ex2 = ""
        option1 = Random.nextInt(1,100)
        do{
            option2 = Random.nextInt(1, 100)
        } while(option2 == option1)
        when(stage){
            7 -> numOperation1 += 1
            16 -> numOperation2 += 1
            25 -> numOperation1 += 1
            35 -> numOperation2 += 1
            45 -> numOperation1 += 1
            55 -> numOperation2 += 1
        }
        var random = Random.nextInt(0,2)
        when(random){
            0 -> {
                ex1 = generateExpression(option1, numOperation1)
                ex2 = generateExpression(option2, numOperation2)
            }
            else -> {
                ex1 = generateExpression(option1, numOperation2)
                ex2 = generateExpression(option2, numOperation1)
            }
        }
        if(option1 < option2){
            optionList.add(Triple(smaller, option1, ex1))
            optionList.add(Triple(!smaller, option2, ex2))
        } else {
            optionList.add(Triple(!smaller, option1, ex1))
            optionList.add(Triple(smaller, option2, ex2))
        }
        setUpCheckbox()
    }

    private fun setUpCheckbox(){
        for (index in optionList.indices){
            var cb = cbList[index]
            cb.isEnabled = true
            cb.isChecked = false
            setBackgroundCheckbox(cbList[index], R.color.grey, R.drawable.rounded_checkbox, R.drawable.checkbox_empty)
            cb.setText(optionList[index].third)
        }
    }

    private fun handleClicked(cbList: MutableList<CheckBox>){
        for (cb in cbList){
            cb.setOnClickListener { showResult() }
        }
    }

    private fun showResult(){
        for (index in cbList.indices){
            var cb = cbList[index]
            if(optionList[index].first){
                setBackgroundCheckbox(cb, R.color.light_green, R.drawable.background_true, R.drawable.checkbox_check)
                if(cb.isChecked){
                    score += if (stage < 7){
                        100
                    } else if (stage < 16){
                        150
                    } else if (stage < 25){
                        200
                    } else if (stage < 35){
                        250
                    } else if (stage < 45){
                        500
                    } else {
                        600
                    }
                }
            } else {
                if(cb.isChecked){
                    setBackgroundCheckbox(cb, R.color.light_red, R.drawable.background_false, R.drawable.checkbox_false)
                }
            }
            cb.isEnabled = false
        }
        stage += 1
        optionList.clear()
        android.os.Handler().postDelayed({
            resetGame()
        }, 2000)
    }

    private fun setBackgroundCheckbox(cb: CheckBox, textColor: Int, drawableBkg: Int, drawableBtn: Int){
        cb.setTextColor(resources.getColor(textColor))
        cb.setBackgroundResource(drawableBkg)
        // Get the existing drawables
        val drawables = cb.compoundDrawablesRelative
        // Update the right drawable
        cb.setCompoundDrawablesRelativeWithIntrinsicBounds(
            drawables[0], // left
            drawables[1], // top
            resources.getDrawable(drawableBtn), // right
            drawables[3]  // bottom
        )
    }

    private fun checkPrimeNumber(value: Int): Boolean{
        for(i in 2..Math.sqrt(value.toDouble()).toInt()){
            if(value % i == 0){
                return false
            }
        }
        return true
    }

    private fun findMultiples(value: Int): MutableList<Int>{
        var multipleList = mutableListOf<Int>()
        for(i in 2..9){
            if (value * i < 100){
                multipleList.add(value * i)
            }
        }
        return multipleList
    }

    private fun findDivisor(value: Int): MutableList<Int>{
        var divisorList = mutableListOf<Int>()
        for(i in 2..9){
            if (value % i == 0){
                divisorList.add(i)
            }
        }
        return divisorList
    }

    private fun generateExpression(value: Int, numOperation: Int): String {
        var index = numOperation
        var number1 = 0
        var number2 = 0
        var expression = ""
        var operation = 0
        if (index == 0) {
            expression = value.toString()
        } else {
            var cloneList = operationList.toMutableList()
            if (checkPrimeNumber(value) || value > 81) {
                cloneList.remove(" x ")
            }
            if (findMultiples(value).size == 0) {
                cloneList.remove(" : ")
            }
            if (value < 2) {
                cloneList.remove(" + ")
            }
            if (value > 98) {
                cloneList.remove(" - ")
            }
            operation = Random.nextInt(0, cloneList.size)
            expression = cloneList[operation]
            if (expression.equals(" + ")) {
                number1 = Random.nextInt(1, value)
                number2 = value - number1
            } else if (expression.equals(" - ")) {
                number1 = Random.nextInt(value + 1, 100)
                number2 = number1 - value
            } else if (expression.equals(" x ")) {
                number1 = findDivisor(value).random()
                number2 = value / number1
            } else {
                number1 = findMultiples(value).random()
                number2 = number1 / value
            }
            expression = if (index > 1) {
                "(" + generateExpression(
                    if (number1 > number2) number1 else number2, index - 1
                ) + ")" + expression + if (number1 > number2) number2.toString() else number1.toString()
            } else{
                number1.toString() + expression + number2.toString()
            }
        }
        return expression
    }
}