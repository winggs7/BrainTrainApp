package com.groups.BrainTrainApp.Components.Memory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.disableAllButton
import com.groups.BrainTrainApp.Utils.drawButton

class RememberColor : AppCompatActivity() {
    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<ButtonCustom> = mutableListOf()
    lateinit var btnBack: Button
    var count = 2
    var numCol = 2
    var playercount = 0
    var lvFlag: Boolean = false
    private var handler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_find_new_image)
        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        totalLayout = findViewById(R.id.totalLayout)
        addButton()
        addButton()
        addButton()
        addButton()
        nextLv()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            drawButton(this, totalLayout, buttonList, numCol)
        }
    }

    private fun addButton() {
        val newButton = ButtonCustom(this)
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
}