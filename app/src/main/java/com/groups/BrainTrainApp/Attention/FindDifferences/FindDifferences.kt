package com.groups.BrainTrainApp.Attention.FindDifferences

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.getResourceId
import java.util.Locale

class FindDifferences : AppCompatActivity() {
    lateinit var btnBack: Button
    private lateinit var imageView: ImageView
    lateinit var btnFindDifferences: ImageButton
    lateinit var scoreText: TextView
    private var arrayImage: Array<Int> = arrayOf()
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_differences)

        imageView = findViewById(R.id.find_differences)
        scoreText = findViewById(R.id.score_text)

        btnFindDifferences = findViewById<ImageButton>(R.id.btn_find_difference)
        btnFindDifferences.setOnClickListener{
            btnFindDifferences.setBackgroundResource(R.drawable.border)
            score+=SCORE_INCREASE
            scoreText.text = "Score: $score"

            Handler(Looper.getMainLooper()).postDelayed({
                getNextImage()
            }, 3000)
        }

        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        getNextImage()
    }

    fun addImage(imageId: Int, imageAttributes: Map<String, Int>) {
        val layoutParams = btnFindDifferences.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = 24
        layoutParams.setMargins(0,208,0,0)

        imageView.setImageResource(imageId)
        btnFindDifferences.layoutParams = layoutParams
        btnFindDifferences.setBackgroundResource(R.drawable.border_none)
    }

    fun getNextImage() {
        val randomId: Int = (0..<allImageList.size).random()
        val imageId = getResourceId(this, "df_" + (randomId + 1).toString())

        val currentImage = allImageList[imageId]
        Log.i("currentImage", currentImage!!.get("x").toString())

        if(arrayImage.isNotEmpty() && allImageList.size == arrayImage.size) {
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