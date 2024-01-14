package com.groups.BrainTrainApp.Components.Memory

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.Datas.easyAnimalImages
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.borderView
import com.groups.BrainTrainApp.Utils.disableAllButton
import com.groups.BrainTrainApp.Utils.drawButton
import com.groups.BrainTrainApp.Utils.enableAllButton
import com.groups.BrainTrainApp.Utils.handleEndGame
import com.groups.BrainTrainApp.Utils.removeBorder
import java.util.Locale
import kotlin.math.roundToInt

class RememberImage : AppCompatActivity() {
    private lateinit var answerLayout: LinearLayout
    private lateinit var questionLayout: LinearLayout
    private val buttonListQuestion: MutableList<ButtonCustom> = mutableListOf()
    private val buttonListAnswer: MutableList<ButtonCustom> = mutableListOf()
    private var imageList: MutableList<Int> = mutableListOf()
    private val countDownTime = 10 //second
    private val clockTime = (countDownTime*1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    var totalPlayTime: Int = 0
    var score = 0
    var lv = 0
    var numberOfButtonRemove = 1
    private lateinit var timer: Timer
    private lateinit var btnBack: AppCompatButton
    private lateinit var btnCheck: Button
    private lateinit var scoreTextView: TextView
    private lateinit var attempTextView : TextView
    private var handler: Handler = Handler(Looper.getMainLooper())
    lateinit var progressBar: ProgressBar
    private val onBackPressedCallBack= object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_remeber_image)
        btnBack = findViewById(R.id.btnback)
        btnBack.setOnClickListener{
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.MEMORY.toString())
            startActivity(intent)
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)
        btnCheck = findViewById(R.id.btnCheck)
        btnCheck.setBackgroundColor(Color.YELLOW)
        btnCheck.setTextColor(Color.BLACK)
        btnCheck.setOnClickListener{
            checkAnswer()
        }
        val layoutBelow: LinearLayout = findViewById(R.id.layoutBelow)
        val layoutScore : LinearLayout = layoutBelow.findViewById(R.id.layoutScore)
        val layoutAttemp : LinearLayout = layoutBelow.findViewById(R.id.layoutAttemp)
        scoreTextView = layoutScore.findViewById(R.id.textScorePoint)
        attempTextView= layoutAttemp.findViewById(R.id.textAttemptPoint)
        timer = object : Timer(clockTime, 1000) {}
        progressBar = findViewById(R.id.progress_bar)
        imageList.addAll(easyAnimalImages)
        questionLayout = findViewById(R.id.questionLayout)
        answerLayout = findViewById(R.id.answerLayout)
        addButton(buttonListQuestion,4,questionLayout,false)
        addButton(buttonListQuestion,4,questionLayout,false)
        addButton(buttonListQuestion,4,questionLayout,false)
        addButton(buttonListQuestion,4,questionLayout,false)
        addButton(buttonListAnswer,4,answerLayout,true)
        addButton(buttonListAnswer,4,answerLayout,true)
        addButton(buttonListAnswer,4,answerLayout,true)
        addButton(buttonListAnswer,4,answerLayout,true)
    }

    fun getRandomImage(): Int {
        val randomIndex = (0 until imageList.size).random()
        return imageList.removeAt(randomIndex)
    }
    fun loadImageIntoButton(targetButton: ButtonCustom) {
        val a = getRandomImage()
        targetButton.setBackgroundResource(a)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            startNextLv()
        }
    }

    private fun addButton(buttonList: MutableList<ButtonCustom>, count: Int, layout: LinearLayout, clickAble: Boolean) {
        val newButton = ButtonCustom(this)
        //  newButton.text = "${buttonList.size}"
        loadImageIntoButton(newButton)
        if(clickAble) {
            newButton.setOnClickListener {
                chosenButton(newButton)
            }
        }
        buttonList.add(newButton)
        buttonList.shuffle()
        drawButton(this,layout,buttonList,count)
    }

    private fun removeButton(numberOfButtonRemove: Int){
        buttonListQuestion.shuffle()
        for (i in 0 until numberOfButtonRemove){
            val answerButtonImage = buttonListQuestion[i].background
            if (answerButtonImage != null) {
               buttonListAnswer[i].background = answerButtonImage
            }
            buttonListAnswer[i].isMark = true
            buttonListQuestion[i].setBackgroundColor(Color.YELLOW)
        }
        buttonListAnswer.shuffle()
        buttonListQuestion.shuffle()
        drawBoth()
    }
    private fun chosenButton(clickedButton: ButtonCustom){
        if(clickedButton.isChoose){
            removeBorder(clickedButton)
            clickedButton.isChoose = false
        }
        else {
            borderView(clickedButton, Color.BLUE)
            clickedButton.isChoose = true
        }
    }

    private fun checkAnswer(){
        timer.pauseTimer()
        btnCheck.setBackgroundColor(Color.LTGRAY)
        btnCheck.isEnabled = false
        var wrongAnswer = false
        for (i in buttonListAnswer.indices) {
            val button = buttonListAnswer[i]
            button.isEnabled = false
            if (button.isChoose xor button.isMark){
                wrongAnswer = true
                if(!button.isMark){
                    borderView(button,Color.RED)
                }
                else{
                    handler.postDelayed({
                        borderView(button,Color.BLUE)
                    }, 2000)
                }
            }
        }
        if (!wrongAnswer){
            score+=100
        }
        handler.postDelayed({
            startNextLv()
        }, 3000)
    }

    private fun startNextLv(){
        lv++
        if (lv==4){
            addButton(buttonListAnswer,4,answerLayout,true)
        }
        if (lv==6){
            numberOfButtonRemove++
        }
        if (lv ==10){
            handleEndGame(this, score, totalPlayTime)
        }

        replaceNewImage(numberOfButtonRemove)
        drawButton(this,questionLayout,buttonListQuestion,4)
        scoreTextView.text = "$score"
        attempTextView.text = "$lv/9"
        answerLayout.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE
        btnCheck.visibility = View.INVISIBLE
        handler.postDelayed({
            removeButton(numberOfButtonRemove)
            answerLayout.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            btnCheck.setBackgroundColor(Color.YELLOW)
            btnCheck.visibility = View.VISIBLE
            btnCheck.isEnabled = true
            setupTimer()
            drawBoth()
        }, 3000)
    }

    private fun replaceNewImage(numberOfButtonRemove:Int){
        for (i in buttonListQuestion.indices){
            loadImageIntoButton(buttonListQuestion[i])
        }
        for (i in buttonListAnswer.indices){
            buttonListAnswer[i].isEnabled = true
            buttonListAnswer[i].isChoose = false
            buttonListAnswer[i].isMark = false
            buttonListAnswer[i].background = null
        }
        for (i in 0 until buttonListAnswer.size-numberOfButtonRemove){
            loadImageIntoButton(buttonListAnswer[i+numberOfButtonRemove])
        }
    }

    private fun drawBoth(){
        drawButton(this,answerLayout,buttonListAnswer,4)
        drawButton(this,questionLayout,buttonListQuestion,4)
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
            Log.i("onFinish", "onFinish")
            handleTimeUp()
        }
        progressBar.max = progressTime.toInt()
        progressBar.progress = progressTime.toInt()
        timer.startTimer()
    }
    private fun onBackPressedMethod() {
        timer.destroyTimer()
        finish()
    }

    private fun handleTimeUp() {
        checkAnswer()
    }
}