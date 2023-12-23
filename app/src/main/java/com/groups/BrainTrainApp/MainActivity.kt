package com.groups.BrainTrainApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.groups.BrainTrainApp.Memory.GameTest

class MainActivity : AppCompatActivity() {
    lateinit var btnRedirect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRedirect = findViewById<Button>(R.id.btnredirect)
        btnRedirect.setOnClickListener{
            startActivity(Intent(this, GameTest::class.java))
        }
    }
}