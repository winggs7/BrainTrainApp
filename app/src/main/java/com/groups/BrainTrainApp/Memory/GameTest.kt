package com.groups.BrainTrainApp.Memory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R

class GameTest : AppCompatActivity() {
    lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_test)

        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}