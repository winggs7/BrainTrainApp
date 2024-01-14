package com.groups.BrainTrainApp.Components.Memory

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Datas.easyMemoryImages
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.borderView
import com.groups.BrainTrainApp.Utils.disableAllButton
import com.groups.BrainTrainApp.Utils.drawButton
import com.groups.BrainTrainApp.Utils.enableAllButton


class FindNewImage : AppCompatActivity() {
    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<ButtonCustom> = mutableListOf()
    lateinit var btnBack: Button
    private var imageList: MutableList<Int> = mutableListOf()
    private var handler: Handler = Handler(Looper.getMainLooper())
    var count = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_find_new_image)
        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        imageList.addAll(easyMemoryImages)
        totalLayout = findViewById(R.id.totalLayout)
        addButton()
        addButton()
        addButton()
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
            drawButton(this,totalLayout,buttonList,count)
        }
    }

    private fun addButton() {
        val newButton = ButtonCustom(this)
      //  newButton.text = "${buttonList.size}"
        loadImageIntoButton(newButton)
        newButton.setOnClickListener {
            chosenButton(newButton)
        }
        buttonList.add(newButton)
        buttonList.shuffle()
        drawButton(this,totalLayout,buttonList,count)
        if (buttonList.size == (count) * (count + 1)) {
            count++
        }
    }
    private fun chosenButton(clickedButton: ButtonCustom){
        if(clickedButton.isChoose){
            Log.d("lose","u lose")
            borderView(clickedButton,Color.RED)
            disableAllButton(buttonList)
            handler.postDelayed({
                for (i in buttonList.indices) {
                    val button = buttonList[i]
                    if(!button.isChoose)
                        borderView(button,Color.BLUE)
                }
            }, 2000)
        }
        else {
            val existingBackground = clickedButton.background
            clickedButton.isChoose = true
            disableAllButton(buttonList)
            //clickedButton.setImageResource(R.drawable.border_square)
//
            borderView(clickedButton, Color.BLUE)

            handler.postDelayed({
                enableAllButton(buttonList)
                addButton()
                clickedButton.background = existingBackground
            }, 3000)
        }
    }


}