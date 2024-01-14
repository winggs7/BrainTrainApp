package com.groups.BrainTrainApp.Components.Attention.FindPairs

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.Datas.easyImages
import com.groups.BrainTrainApp.Enum.Level
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.borderView
import com.groups.BrainTrainApp.Utils.drawButton
import com.groups.BrainTrainApp.Utils.handleEndGame
import com.groups.BrainTrainApp.Utils.handleProgressBar
import com.groups.BrainTrainApp.Utils.removeBorder
import java.text.DecimalFormat
import kotlin.math.roundToInt


class FindPairs : AppCompatActivity() {
    lateinit var container: LinearLayout
    lateinit var imageList: Array<Int>
    lateinit var scoreView: TextView
    lateinit var btnBack: Button
    var buttonList: MutableList<ButtonCustom> = mutableListOf()
    var chosenList: MutableList<ButtonCustom> = mutableListOf()

    var currentLevel = Level.EASY
    var score: Int = 0
    var correctNum: Int = 0
    var currentRound: Int = 1

    var count: Int = 4

    //TODO declare attributes for timer
    var totalPlayTime: Int = 0
    lateinit var progressBar: ProgressBar
    private val countDownTime = 20 //second
    private val clockTime = (countDownTime*1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    private lateinit var timer: Timer
    private val onBackPressedCallBack= object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pairs)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)

        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.ATTENTION.toString())
            startActivity(intent)
        }

        progressBar = findViewById(R.id.progress_bar)
//        progressText = findViewById(R.id.progress_count)
//        progressText.text = time.toString() + "s"

        container = findViewById(R.id.find_pair_container)
        scoreView = findViewById(R.id.score_view)
        imageList = easyImages

        setupTimer()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if(hasFocus) {
            resetGame()
        }
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
        timer = object : Timer(clockTime, 1000) {}
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

    private fun timerFormat(secondLeft: Int, timeTxt: TextView) {
        progressBar.progress = secondLeft
    }

    private fun onBackPressedMethod() {
        timer.destroyTimer()
        finish()
    }

    private fun resetGame() {
        if (currentRound > MAX_ROUND) {
            handleTimeUp()
        }

        val randomList = handleRandomImage(imageList)
        for (i in randomList.indices) {
            addPairs(randomList[i])
        }
    }

    private fun handleRandomImage(list: Array<Int>): Array<Int> {
        var randomList = list.asSequence().take(currentRound + 2).toList().toTypedArray()
        randomList += randomList
        randomList.shuffle()
        return randomList
    }

    private fun addPairs(image: Int) {
        val newButton = ButtonCustom(this)

        newButton.setBackgroundResource(image)
        newButton.backgroundResourceId = image
        newButton.setOnClickListener {
            handleClickButton(newButton)
        }
        buttonList.add(newButton)
        drawButton(this, container, buttonList, count).requestLayout()
    }

    private fun handleClickButton(clickedButton: ButtonCustom){
        if(clickedButton.isChoose){
            //TODO handle LOSE situation
        }

        clickedButton.isChoose = !clickedButton.isChoose
        if(chosenList.size < 1) {
            borderView(clickedButton, Color.RED)

            drawButton(this, container, buttonList, count).requestLayout()
            chosenList.add(clickedButton)
            Log.i("Size", chosenList.size.toString())
        } else {
            borderView(clickedButton, Color.RED)

            drawButton(this, container, buttonList, count).requestLayout()
            chosenList.add(clickedButton)
            compare()
            chosenList = mutableListOf()
            buttonList.forEach { it.isChoose = false }

            Handler(Looper.getMainLooper()).postDelayed({
                drawButton(this, container, buttonList, count).requestLayout()
            }, 500)
        }
    }

    private fun compare() {
        if (chosenList.size == 2 && chosenList[0].backgroundResourceId == chosenList[1].backgroundResourceId) {
            score += SCORE_INCREASE
            scoreView.text = score.toString()
            correctNum++

            buttonList.map {
                if (it.backgroundResourceId == chosenList[0].backgroundResourceId) {
                    //TODO change success background
                    Log.i("Correct", correctNum.toString())
                    it.setBackgroundResource(R.drawable.success_icon)
                }
            }

            if (correctNum == currentRound + 2) {
                currentRound++
                correctNum = 0
                buttonList = mutableListOf()

                progressBar.progress = progressTime.toInt()
                timer.restartTimer()
                timer.startTimer()

                Handler(Looper.getMainLooper()).postDelayed({
                    resetGame()
                }, 500)
            }
        } else {
            chosenList.map {
                removeBorder(it)
            }
        }
    }

    private fun handleTimeUp() {
        //TODO handle lose
        handleEndGame(this, score, totalPlayTime)
    }
}
