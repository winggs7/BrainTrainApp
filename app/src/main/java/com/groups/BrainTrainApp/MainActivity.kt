package com.groups.BrainTrainApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groups.BrainTrainApp.Adapter.GameAdapter
import com.groups.BrainTrainApp.Components.Attention.FindPairs.FindPairs
import com.groups.BrainTrainApp.Model.Game

class MainActivity : AppCompatActivity() {
    private lateinit var gameRecyclerView: RecyclerView
    private lateinit var gameArrayList: ArrayList<Game>
    lateinit var imageId: Array<Int>
    lateinit var nameGame: Array<String>

    lateinit var btnRedirect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        renderGameRecycleView()

        btnRedirect = findViewById<Button>(R.id.btnredirect)
        btnRedirect.setOnClickListener {
            startActivity(Intent(this, FindPairs::class.java))
        }
    }

    fun renderGameRecycleView() {
        imageId = arrayOf(
            R.drawable.brain,
            R.drawable.puzzle
        )

        nameGame = arrayOf(
            "Brain game",
            "Puzzle game"
        )

        gameRecyclerView = findViewById(R.id.gameRecyclerView)
        gameRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gameRecyclerView.setHasFixedSize(true)
        gameArrayList = arrayListOf<Game>()

        for (i in imageId.indices) {
            val game = Game(nameGame[i], imageId[i])
            gameArrayList.add(game)
        }

        val gameAdapter = GameAdapter(gameArrayList);
        gameRecyclerView.adapter = gameAdapter
        gameAdapter.setOnClickListener(object : GameAdapter.OnClickListener {
            override fun onClick(position: Int, model: Game) {
                // TODO: Handle Intent to redirect
                Log.i("GameItem", model.name)
                handleRedirect(this, FindPairs::class.java)
            }
        })
    }

    private fun handleRedirect(onClickListener: GameAdapter.OnClickListener, java: Class<*>): Intent? {
        startActivity(Intent(this, java))
        return null
    }
}