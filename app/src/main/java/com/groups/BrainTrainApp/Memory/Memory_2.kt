package com.groups.BrainTrainApp.Memory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.drawButton

class Memory_2 : AppCompatActivity() {
    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<ButtonCustom> = mutableListOf()
    lateinit var btnBack: Button
    var count = 2
    var numCol = 2
    var playercount = 0
    var lvFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_game_1)
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
            clickedButton.setBackgroundColor(Color.BLUE)
            playercount++
            if (playercount == count) {
                playercount = 0
                count++
                clearColor()
                nextLv()
            }
        } else {
            clickedButton.setBackgroundColor(Color.RED)
            Log.d("lose", "u lose")
        }

    }

    private fun clearColor() {
        for (i in buttonList.indices) {
            val button = buttonList[i]
            button.setBackgroundColor(Color.WHITE)
        }
    }

    private fun nextLv() {
        for (i in 0 until count) {
            buttonList.get(i).isChoose = true
        }
        if (!lvFlag) {
            lvFlag = true
            numCol++
        } else {
            lvFlag = false

        }
        for (i in 0 until count) {
            addButton()
        }
    }
}