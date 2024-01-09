package com.groups.BrainTrainApp

import GameType
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.groups.BrainTrainApp.Adapter.GameAdapter
import com.groups.BrainTrainApp.Components.Common.GameEnd
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.Datas.mainPageList
import com.groups.BrainTrainApp.Components.Attention.FindPairs.FindPairs
import com.groups.BrainTrainApp.Memory.Memory_1
import com.groups.BrainTrainApp.Memory.Memory_2
import com.groups.BrainTrainApp.Model.Game
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        renderGameViewPager(mainPageList)
        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000)
            }
        })

//        val intent = Intent(this, Memory_1::class.java)
//        startActivity(intent)
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
                } else if (model.className == null) {
                    handleChangeRecycleViewTest(model.type)
                }
            }
        })
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

    private fun handleChangeRecycleViewTest(type: GameType) {
        val intent = Intent(this, GameSelected::class.java)
        intent.putExtra("type", type.toString())
        startActivity(intent)
    }

    private fun handleRedirectTest(java: Class<*>): Intent? {
        startActivity(Intent(this, java))
        return null
    }
}