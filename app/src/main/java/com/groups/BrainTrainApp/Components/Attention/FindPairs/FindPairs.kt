package com.groups.BrainTrainApp.Components.Attention.FindPairs

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Enum.Level
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.drawButton
import com.groups.BrainTrainApp.Utils.handleProgressBar


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

    var time = 20
    lateinit var progressBar: ProgressBar
    lateinit var progressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pairs)

        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_count)
        progressText.text = time.toString() + "s"

        container = findViewById(R.id.find_pair_container)
        scoreView = findViewById(R.id.find_pair_score)
        imageList = easyImages
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if(hasFocus) {
            resetGame()
            handleProgressBar(progressBar, progressText, time, ::handleTimeUp)
        }
    }

    private fun resetGame() {
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
            drawButton(this, container, buttonList, count).requestLayout()
            chosenList.add(clickedButton)
            Log.i("Size", chosenList.size.toString())
        } else {
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
                Handler(Looper.getMainLooper()).postDelayed({
                    resetGame()
                }, 500)
            }
        }
    }

    private fun handleTimeUp() {
        //TODO handle lose
        val text = "Timeup!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(this, text, duration)
        toast.show()
    }
}
