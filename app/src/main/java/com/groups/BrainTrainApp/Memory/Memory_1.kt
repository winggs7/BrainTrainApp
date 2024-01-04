package com.groups.BrainTrainApp.Memory

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.borderButton
import com.groups.BrainTrainApp.Utils.drawButton
import java.util.Locale

class Memory_1 : AppCompatActivity() {
    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<ButtonCustom> = mutableListOf()
    private val imageNames: MutableList<String> = mutableListOf()
    lateinit var btnBack: Button
    private var handler: Handler = Handler(Looper.getMainLooper())
    var count = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_game_1)
        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        createListImage()
        totalLayout = findViewById(R.id.totalLayout)
        addButton()
        addButton()
        addButton()
    }

    private fun createListImage(){
        for (i in 1 until 10){
            imageNames.add("m$i")
        }
    }
    fun getRandomImageName(): String {

        val randomIndex = (0 until imageNames.size).random()

        return imageNames.removeAt(randomIndex)
    }
    fun loadImageIntoButton(targetButton: ButtonCustom) {
        var a = getResourceId(this,getRandomImageName())
        targetButton.setBackgroundResource(a)
    }
    fun getResourceId(context: Context, name: String): Int {
        var name = name.lowercase(Locale.getDefault())
        return context.resources.getIdentifier(name, "drawable", context.packageName)
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
    }
    private fun chosenButton(clickedButton: ButtonCustom){
        if(clickedButton.isChoose){
            Log.d("lose","u lose")
        }
        val existingBackground = clickedButton.background
        clickedButton.isChoose = true
        //clickedButton.setImageResource(R.drawable.border_square)
//
       borderButton(clickedButton,Color.BLUE)

        handler.postDelayed({

            addButton()
            clickedButton.background = existingBackground
        }, 3000)
    }


}