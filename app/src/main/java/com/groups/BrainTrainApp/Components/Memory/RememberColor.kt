package com.groups.BrainTrainApp.Components.Memory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.disableAllButton
import com.groups.BrainTrainApp.Utils.drawButton
import com.groups.BrainTrainApp.Utils.handleEndGame

class RememberColor : AppCompatActivity() {
    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<ButtonCustom> = mutableListOf()
    lateinit var btnBack: Button
    var count = 2
    var numCol = 2
    var playercount = 0
    var lvFlag: Boolean = false
    private lateinit var timer: Timer
    private val clockTime = (9999*1000).toLong()
    var totalPlayTime: Int = 0
    var score = 0
    private var handler: Handler = Handler(Looper.getMainLooper())

    private val onBackPressedCallBack= object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_find_new_image)
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentById(R.id.level_container)!!).commit()
        timer = object : Timer(clockTime, 1000) {}
        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener {
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.MEMORY.toString())
            startActivity(intent)
        }
        totalLayout = findViewById(R.id.totalLayout)
        addButton()
        addButton()
        addButton()
        addButton()
        nextLv()
        setupTimer()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            drawButton(this, totalLayout, buttonList, numCol)
        }
    }

    private fun addButton() {
        val newButton = ButtonCustom(this,true)
        newButton.setOnClickListener {
            chosenButton(newButton)
        }
        newButton.setBackgroundColor(Color.WHITE)
        buttonList.shuffle()
        buttonList.add(newButton)
        drawButton(this, totalLayout, buttonList, numCol)
    }

    private fun chosenButton(clickedButton: ButtonCustom) {
        if (clickedButton.isChoose) {
            clickedButton.isChoose = false
            clickedButton.isEnabled = false
            clickedButton.setBackgroundColor(Color.BLUE)
            playercount++
            score+=100
            if (playercount == count) {
                disableAllButton(buttonList)
                playercount = 0
                count++
                handler.postDelayed({
                    clearColor()
                    nextLv()
                }, 2000)
            }
        } else {
            clickedButton.setBackgroundColor(Color.RED)
            appearColor()
            handleEndGame(this,score,totalPlayTime)
            disableAllButton(buttonList)
        }
    }

    private fun clearColor() {
        for (i in buttonList.indices) {
            val button = buttonList[i]
            button.setBackgroundColor(Color.WHITE)
            button.isEnabled = true
        }
    }

    private fun appearColor() {
        for (i in buttonList.indices) {
            val button = buttonList[i]
            if(button.isChoose)
                button.setBackgroundColor(Color.BLUE)
        }
    }


    private fun nextLv() {
        for (i in 0 until count) {
            buttonList.get(i).isChoose = true
            buttonList.get(i).setBackgroundColor(Color.BLUE)
        }
        for (i in 0 until numCol) {
            addButton()
        }
        disableAllButton(buttonList)
        if (!lvFlag) {
            lvFlag = true
            numCol++
        } else {
            lvFlag = false
        }
        handler.postDelayed({
            clearColor()
        }, 3000)
    }

    private fun setupTimer() {

        timer.onTick = { millisUntilFinished ->
            totalPlayTime++
        }
        timer.startTimer()
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
    private fun onBackPressedMethod() {
        timer.destroyTimer()
        finish()
    }
}