package com.groups.BrainTrainApp.Components.Attention.FindDifferences

import GameType
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Components.Common.LevelViewModel
import com.groups.BrainTrainApp.Components.Common.Timer
import com.groups.BrainTrainApp.Enum.Level
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.getResourceId
import com.groups.BrainTrainApp.Utils.handleEndGame
import kotlin.math.roundToInt

class FindDifferences : AppCompatActivity() {
    private lateinit var imageView: ImageView
    lateinit var btnFindDifferences: ImageButton
    lateinit var btnBack: Button
    lateinit var scoreView: TextView
    private var arrayImage: Array<Int> = arrayOf()
    var score: Int = 0

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
        setContentView(R.layout.activity_find_differences)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallBack)

        imageView = findViewById(R.id.find_differences)
        scoreView = findViewById(R.id.score_view)
        btnBack = findViewById<Button>(R.id.btnback)
        btnFindDifferences = findViewById<ImageButton>(R.id.btn_find_difference)
        progressBar = findViewById(R.id.progress_bar)

        setupTimer()

        btnFindDifferences.setOnClickListener{
            btnFindDifferences.setBackgroundResource(R.drawable.border)
            score+= SCORE_INCREASE
            scoreView.text = score.toString()

            progressBar.progress = progressTime.toInt()
            timer.restartTimer()
            timer.startTimer()

            Handler(Looper.getMainLooper()).postDelayed({
                getNextImage()
            }, 3000)
        }


        btnBack.setOnClickListener{
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.ATTENTION.toString())
            startActivity(intent)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            getNextImage()
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


    private fun onBackPressedMethod() {
        timer.destroyTimer()
        finish()
    }

    private fun handleTimeUp() {
        //TODO handle lose
        handleEndGame(this, score, totalPlayTime)
    }

    private fun addImage(imageId: Int, imageAttributes: Map<String, Float>) {
        if(imageAttributes.isNotEmpty()) {
            imageView.setImageResource(imageId)
            btnFindDifferences.setBackgroundResource(R.drawable.border_none)

            Handler(Looper.getMainLooper()).postDelayed({
                val paramButton = LinearLayout.LayoutParams(
                    (imageView.width * imageAttributes["w"]!!).toInt(),
                    (imageView.height * imageAttributes["h"]!!).toInt())
                btnFindDifferences.layoutParams = paramButton
                btnFindDifferences.x = imageView.width * imageAttributes["x"]!!
                btnFindDifferences.y = imageView.height * imageAttributes["y"]!!

                Log.i("W", (imageView.width * imageAttributes["w"]!!).toInt().toString())
                Log.i("Y", (imageView.height * imageAttributes["y"]!!).toInt().toString())

//              btnFindDifferences.setBackgroundResource(R.drawable.border_none)
//                btnFindDifferences.setBackgroundResource(R.drawable.border)
            }, 500)

        }
    }

    private fun getNextImage() {
        val randomId: Int = (0..<allImageList.size).random()
        val imageId = getResourceId(this, "df_" + (randomId + 1).toString())

        val currentImage = allImageList[imageId]
        Log.i("currentImage", currentImage!!.get("x").toString())

        if(arrayImage.isNotEmpty() && allImageList.size == arrayImage.size) {
            handleTimeUp()
            return
        }

        if (arrayImage.contains(randomId)) {
            getNextImage()
        } else {
            arrayImage += randomId
            addImage(imageId!!,currentImage!!)
        }
    }
}