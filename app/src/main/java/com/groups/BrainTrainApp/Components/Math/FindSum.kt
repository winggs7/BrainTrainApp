package com.groups.BrainTrainApp.Components.Math

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayout
import com.groups.BrainTrainApp.Components.Attention.FindPairs.MAX_ROUND
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Components.Common.LevelViewModel
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.Enum.Level
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.handleEndGame
import kotlin.math.roundToInt
import kotlin.random.Random


class FindSum : AppCompatActivity() {
    private val viewModel: LevelViewModel by viewModels()

    private lateinit var answerLayout: FlexboxLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var btnBack: AppCompatButton
    private lateinit var question: TextView
    private lateinit var stageTxt: TextView
    private lateinit var scoreTxt: TextView
    private var chosenCheckBoxId = mutableListOf<Int>()
    private var cbLst = mutableListOf<CheckBox>()
    private var optionLst = mutableListOf<Pair<Boolean, Int>>()
    private var options = mutableListOf<Int>()
    private lateinit var timer: Timer
    private val countDownTime = 20 //second
    private val clockTime = (countDownTime*1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    var totalPlayTime: Int = 0
    private var sumTemp = 10
    private var sum = 0
    private var stage = 1
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_find_sum)

        viewModel.selectedLevel.observe(this, Observer { level ->
            //TODO handle game's difficulty
            var levelNum: Int = 1
            when (level) {
                Level.EASY -> {
                    sumTemp = 10
                }
                Level.NORMAL -> {
                    sumTemp = 100
                    levelNum = 2
                }
                else -> {
                    sumTemp = 1000
                    levelNum = 3
                }
            }
            findViewById<TextView>(R.id.level_txt).text = "Cấp độ: " + levelNum.toString()
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.level_container)!!).commit()
            init()
        })
        btnBack = findViewById(R.id.button_back)
        btnBack.setOnClickListener{
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.MATH.toString())
            startActivity(intent)
        }
        question = findViewById(R.id.question)
        progressBar = findViewById(R.id.progress_bar)
        answerLayout = findViewById(R.id.answer_layout)
        stageTxt = findViewById(R.id.question_number)
        scoreTxt = findViewById(R.id.score_txt)
        timer = object : Timer(clockTime, 1000) {}
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            drawCheckBox()
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

    private fun init(){
        addCheckBox()
        addCheckBox()
        addCheckBox()
        addCheckBox()
        resetGame()
    }

    private fun resetGame(){
        if (stage > 15) {
            handleTimeUp()
        } else {
            question.setText("Hai số nào dưới đây có tổng bằng ${sumTemp}?")
            stageTxt.setText("Câu hỏi: ${stage}")
            scoreTxt.setText("Điểm số: ${score}")
            setupTimer()
            generateOptions()
            setCheckBoxs()
            drawCheckBox()
        }
    }

    private  fun addCheckBox(){
        var cb = CheckBox(this)
        cb.isSoundEffectsEnabled = false
        cb.setBackgroundResource(R.drawable.rounded_checkbox)
        cb.setPadding(40,40,40,40)
        cb.textSize = 40F
        cb.setTextColor(resources.getColor(R.color.grey))
        cb.apply {
            textSize = 25f // 40sp
            buttonDrawable = null
            setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.checkbox_empty,
                0
            )
        }
        attachListener(cb)
        cbLst.add(cb)
    }

    private fun drawCheckBox(){
        answerLayout.removeAllViews()
        for (cb in cbLst){
            cb.layoutParams = ViewGroup.MarginLayoutParams(answerLayout.width / 2 * 80 / 100, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
            val param = cb.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(20,20,30,30)
            cb.layoutParams = param
            answerLayout.addView(cb)
        }
    }


    private fun attachListener(cb: CheckBox){
        cb.setOnClickListener {
            var number = Integer.parseInt(cb.text.toString())
            var cbId = cbLst.indexOf(cb)
            if(chosenCheckBoxId.size < 1){
                sum -= number
                setBackgroundCheckbox(cb, R.color.grey, R.drawable.rounded_checkbox, R.drawable.checkbox_chosen)
                chosenCheckBoxId.add(cbId)
            } else if(cbId in chosenCheckBoxId){
                sum += number
                setBackgroundCheckbox(cb, R.color.grey, R.drawable.rounded_checkbox, R.drawable.checkbox_empty)
                chosenCheckBoxId.remove(cbId)
            } else {
                sum -= number
                if (sum == 0){
                    if(stage < 6){
                        score += 200
                    } else if (stage < 9){
                        score += 400
                    } else if (stage < 12){
                        score += 600
                    } else {
                        score += 800
                    }
                }
                stage += 1
                showResult()
                android.os.Handler().postDelayed({
//                    //restart timer
//                    progressBar.progress = progressTime.toInt()
//                    timer.restartTimer()
//                    timer.startTimer()
                    resetGame()
                }, 2000)
            }
        }
    }

    private fun setBackgroundCheckbox(cb: CheckBox, textColor: Int, drawableBkg: Int, drawableBtn: Int){
        cb.setTextColor(resources.getColor(textColor))
        cb.setBackgroundResource(drawableBkg)
        // Get the existing drawables
        val drawables = cb.compoundDrawablesRelative
        // Update the right drawable
        cb.setCompoundDrawablesRelativeWithIntrinsicBounds(
            drawables[0], // left
            drawables[1], // top
            resources.getDrawable(drawableBtn), // right
            drawables[3]  // bottom
        )
    }

    private fun showResult(){
        var cb: CheckBox
        for(index in cbLst.indices){
            cb = cbLst[index]
            if(optionLst[index].first){
                setBackgroundCheckbox(cb, R.color.light_green, R.drawable.background_true, R.drawable.checkbox_check)
            } else{
                if(cb.isChecked){
                    setBackgroundCheckbox(cb, R.color.light_red, R.drawable.background_false, R.drawable.checkbox_false)
                } else{
                    setBackgroundCheckbox(cb, R.color.grey, R.drawable.rounded_checkbox, R.drawable.checkbox_empty)
                }
            }
            cb.isEnabled = false
        }
        optionLst.clear()
        options.clear()
        chosenCheckBoxId.clear()
    }

    private fun generateOptions(){
        sum = sumTemp
        var index = 0
        var position = 0
        var value1 = 0
        var value2 = 0
        options.addAll((sum/10..sum/2).toList())
        options.addAll((sum/2..sum-sum/10).toList())
        var cloneOptions = options.toMutableList()
        if(stage == 6 || stage == 10){
            addCheckBox()
        }
        while (index < cbLst.size){
            position = Random.nextInt(0, cloneOptions.size)
            value1 = cloneOptions[position]
            value2 = cloneOptions[cloneOptions.size - position - 1]
            if(index == 0){
                optionLst.add(Pair(true, value1))
                optionLst.add(Pair(true, value2))
                index += 1
            } else {
                optionLst.add(Pair(false,value1))
            }
            cloneOptions.remove(value1)
            cloneOptions.remove(value2)
            index += 1
        }
    }
    private fun setCheckBoxs(){
        optionLst.shuffle()
        for (index in cbLst.indices){
            cbLst[index].isEnabled = true
            cbLst[index].isChecked = false
            setBackgroundCheckbox(cbLst[index], R.color.grey, R.drawable.rounded_checkbox, R.drawable.checkbox_empty)
            cbLst[index].setText(optionLst[index].second.toString())
        }
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
            handleTimeUp()
        }
        progressBar.max = progressTime.toInt()
        progressBar.progress = progressTime.toInt()
        timer.startTimer()
    }

    private fun handleTimeUp() {
        //TODO handle lose
        handleEndGame(this, score, totalPlayTime)
    }
}