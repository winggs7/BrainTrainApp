package com.groups.BrainTrainApp.Components.Common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R

class GameEnd : AppCompatActivity() {
    private lateinit var backBtn: Button
    private lateinit var scoreText: TextView
    private lateinit var timeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_end)

        backBtn = findViewById(R.id.btn_back)
        scoreText = findViewById(R.id.score_textview)
        timeText = findViewById(R.id.time_textview)

        val score = intent.getStringExtra("score")
        val time = intent.getStringExtra("time")
        scoreText.text = "Score: $score"
        timeText.text = "Time: $time"

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}