package com.groups.BrainTrainApp.Components.Memory

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Components.Common.LevelViewModel
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Datas.normalFoodImages
import com.groups.BrainTrainApp.Enum.Level
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.borderView
import com.groups.BrainTrainApp.Utils.disableAllButton
import com.groups.BrainTrainApp.Utils.drawButton
import com.groups.BrainTrainApp.Utils.enableAllButton
import com.groups.BrainTrainApp.Utils.handleEndGame
import kotlin.math.roundToInt


class FindNewImage : AppCompatActivity() {
    private val viewModel: LevelViewModel by viewModels()

    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<ButtonCustom> = mutableListOf()
    lateinit var btnBack: AppCompatButton
    private lateinit var timer: Timer
    private val clockTime = (9999*1000).toLong()
    var totalPlayTime: Int = 0
    var score = 0
    private var imageList: MutableList<Int> = mutableListOf()
    private var handler: Handler = Handler(Looper.getMainLooper())
    var count = 3
    private val onBackPressedCallBack= object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_find_new_image)

        viewModel.selectedLevel.observe(this, Observer { level ->
            //TODO handle game's difficulty
            when (level) {
                Level.EASY -> {

                }
                Level.NORMAL -> {

                }
                else -> {

                }
            }
        })
        timer = object : Timer(clockTime, 1000) {}
        btnBack = findViewById<AppCompatButton>(R.id.btnback)
        btnBack.setOnClickListener{
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.MEMORY.toString())
            startActivity(intent)
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)
        imageList.addAll(normalFoodImages)
        totalLayout = findViewById(R.id.totalLayout)
        addButton()
        addButton()
        addButton()
        setupTimer()
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
                handler.postDelayed({
                    handleEndGame(this, score, totalPlayTime)
                }, 1000)

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
                score+=100
                addButton()
                clickedButton.background = existingBackground
            }, 3000)
        }
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