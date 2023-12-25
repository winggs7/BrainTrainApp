package com.groups.BrainTrainApp.Memory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
class Memory_2 : AppCompatActivity() {
    private lateinit var totalLayout: LinearLayout
    private val buttonList: MutableList<MyButton> = mutableListOf()
    lateinit var btnBack: Button
    var count = 2
    var playercount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_game_1)
        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        totalLayout = findViewById(R.id.totalLayout)
        addButton()
        addButton()
        addButton()
        addButton()
        addButton()
        addButton()
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            drawButtons()
        }
    }

    private fun addButton() {
        val newButton = MyButton(this)
        newButton.text = "${buttonList.size}"
        newButton.setOnClickListener {
            chosenButton(newButton)
        }
        buttonList.add(newButton)
        buttonList.shuffle()
        drawButtons()
    }
    private fun chosenButton(clickedButton: MyButton){
        if(clickedButton.isChoose){
            clickedButton.isChoose = false
        }
        else{
            Log.d("lose","u lose")
        }
        playercount++
        if(playercount == count){
            nextLv();
        }

    }
    private fun nextLv() {

    }
    private fun drawButtons() {
        totalLayout.removeAllViews()

        var currentRow: LinearLayout? = null
        val a = totalLayout.width

//        if(totalLayout.height <= (totalLayout.width/count+50) *  ceil((buttonList.size/count).toFloat())){
//            count ++
//        }
        if(buttonList.size == (count)*(count+1)){
            count ++
        }
        for (i in buttonList.indices) {

            val button = buttonList[i]

            button.layoutParams = ViewGroup.LayoutParams(a/count, a/count)
            if (button.parent != null) {
                (button.parent as? ViewGroup)?.removeView(button)
            }


            if (i % count == 0) {
                currentRow = LinearLayout(this)
                currentRow.orientation = LinearLayout.HORIZONTAL
                currentRow.setHorizontalGravity(1)
                totalLayout.addView(currentRow)
            }
            currentRow?.addView(button)

        }
        totalLayout.requestLayout()

    }
}