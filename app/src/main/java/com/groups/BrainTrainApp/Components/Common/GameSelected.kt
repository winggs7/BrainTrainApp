package com.groups.BrainTrainApp.Components.Common

import GameType
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.groups.BrainTrainApp.Adapter.GameAdapter
import com.groups.BrainTrainApp.Datas.attentionList
import com.groups.BrainTrainApp.Datas.languageList
import com.groups.BrainTrainApp.Datas.mainPageList
import com.groups.BrainTrainApp.Datas.mathList
import com.groups.BrainTrainApp.Datas.memoryList
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.Model.Game
import com.groups.BrainTrainApp.R
import kotlin.math.abs

class GameSelected : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var gameAdapter: GameAdapter
    lateinit var btnHome: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnHome = findViewById(R.id.btn_home)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val type = intent.getStringExtra("type")
        val typeGame : GameType = enumValueOf(type!!)
        if (typeGame == GameType.ATTENTION) {
            renderGameViewPager(attentionList)
        } else if (typeGame == GameType.LANGUAGE) {
            renderGameViewPager(languageList)
        } else if (typeGame == GameType.MEMORY) {
            renderGameViewPager(memoryList)
        } else if (typeGame == GameType.MEMORY) {
           renderGameViewPager(mathList)

        }
        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, 5000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun renderGameViewPager(list: ArrayList<Game>) {
        viewPager2 = findViewById(R.id.viewPager2)
        handler = Handler(Looper.myLooper()!!)

        gameAdapter = GameAdapter(list, viewPager2)
        viewPager2.adapter = gameAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2!!.getChildAt(0)!!.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        gameAdapter.setOnClickListener(object : GameAdapter.OnClickListener {
            override fun onClick(position: Int, model: Game) {
                if (model.className != null) {
                    handleRedirectTest(model.className!!)
                }
            }
        })
    }

    private fun handleRedirectTest(java: Class<*>): Intent? {
        startActivity(Intent(this, java))
        return null
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(100))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        viewPager2.setPageTransformer(transformer)
    }
}