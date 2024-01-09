package com.groups.BrainTrainApp.Components.Attention.Ship

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.Components.Common.GameSelected
import com.groups.BrainTrainApp.MainActivity
import com.groups.BrainTrainApp.R
import com.groups.BrainTrainApp.Utils.handleProgressBar
import kotlin.random.Random

class Ship : AppCompatActivity() {
    lateinit var parentLayout: ConstraintLayout
    lateinit var container: GridLayout
    lateinit var btnBack: Button
    lateinit var scoreView: TextView
    lateinit var spawnShark: ConstraintLayout

    var level: Int = 1
    var pointPerShip: Int = 1000
    var totalScore: Int = 0

    var shipNum: Int = 3
    var sharkNum: Int = 3

    var listShark: MutableList<ImageView> = mutableListOf()
    var listShip: MutableList<ButtonCustom> = mutableListOf()

    var widthPerGridItem: Int = 0
    var heightPerGridItem: Int = 0
    val shipMatrix: Array<IntArray> = arrayOf(
        IntArray(SHIP_COL) { it },
        IntArray(SHIP_ROW) { it }
    )
    var randomShipCol: Array<Int> = arrayOf()
    var randomShipRow: Array<Int> = arrayOf()

    var time = 20
    lateinit var progressBar: ProgressBar
    lateinit var progressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship)

        parentLayout = findViewById(R.id.parentLayout)
        spawnShark = findViewById(R.id.shark_spawn)

        btnBack = findViewById<Button>(R.id.btnback)
        btnBack.setOnClickListener {
            val intent = Intent(this, GameSelected::class.java)
            intent.putExtra("type", GameType.ATTENTION.toString())
            startActivity(intent)
        }
        container = findViewById(R.id.ship_container)

        scoreView = findViewById(R.id.score)
        totalScore = pointPerShip * 3
        scoreView.text = totalScore.toString()

        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_count)
        progressText.text = time.toString() + "s"
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            widthPerGridItem = container.width / SHIP_COL
            heightPerGridItem = container.height / SHIP_ROW

//            resetGame()
            drawButton()

            for (i in 0..<sharkNum) {
                drawShark(i)
            }
            handleProgressBar(progressBar, progressText, time, ::handleTimeUp)
        }
    }

    private fun drawButton() {
        randomShipCol =
            IntArray(SHIP_COL - 2) { it + 1 }.asSequence().shuffled().take(shipNum).toList()
                .toTypedArray()
        randomShipRow =
            IntArray(SHIP_ROW - 2) { it + 1 }.asSequence().shuffled().take(shipNum).toList()
                .toTypedArray()

        for (i in 0..<SHIP_ROW) {
            for (j in 0..<SHIP_COL) {
                val newButton = ButtonCustom(this)

                if (checkShipNum(j, i, randomShipCol, randomShipRow)) {
                    newButton.setBackgroundResource(R.drawable.ship)
                    newButton.backgroundResourceId = R.drawable.ship
                    listShip.add(newButton)
                    listShip.plus(Pair(newButton, pointPerShip))
                } else {
                    newButton.setBackgroundResource(R.color.white)
                }

//        newButton.backgroundResourceId = image
                newButton.setOnClickListener {
                    handleClickButton(newButton)
                }

                val param = GridLayout.LayoutParams()
                param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                param.width = 0
                param.height = 0

                newButton.layoutParams = param

                container.addView(newButton)
            }
        }

    }

    private fun checkShipNum(
        one: Int,
        two: Int,
        arrayOne: Array<Int>,
        arrayTwo: Array<Int>
    ): Boolean {
        for (i in arrayOne.indices) {
            if (arrayOne[i] == one && arrayTwo[i] == two) {
                return true
            }
        }

        return false
    }

    private fun resetGame() {
        listShark = mutableListOf()
        listShip = mutableListOf()
        randomShipCol = arrayOf()
        randomShipRow = arrayOf()

        totalScore = pointPerShip*3
        scoreView.text = totalScore.toString()
        container.removeAllViews()
        spawnShark.removeAllViews()

        drawButton()

        for (i in 0..<sharkNum) {
            drawShark(i)
        }
    }

    private fun handleClickButton(button: ButtonCustom) {
        button.isChoose = true

        if (button.isChoose && button.backgroundResourceId == R.drawable.ship) {
            //TODO handle catch shark

            val xStartRange = button.x - widthPerGridItem
            val yStartRange = button.y - heightPerGridItem
            val xEndRange = button.x + (widthPerGridItem * 2)
            val yEndRange = button.y + (heightPerGridItem * 2)

            Log.i("Shark list", listShark.size.toString())

            val iterator = listShark.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()

//                Log.i("Shark list", item.toString())

                if (listShark.contains(item!!)) {
                    if (item.x < xEndRange && item.x > xStartRange &&
                        item.y < yEndRange && item.y > yStartRange
                    ) {
                        listShark.removeIf { listShark.contains(it) }
                        spawnShark.removeView(item)

                        if (spawnShark.childCount < 3) {
                            drawShark(listShip.indexOf(button))
                        }

                        Log.i("sharkNum handleClickButton", sharkNum.toString())
                        return
                    }
                }
            }
        }

        button.setImageResource(R.drawable.border)
        Handler(Looper.getMainLooper()).postDelayed({
            button.setImageResource(R.drawable.border_none)
            button.isChoose = false
        }, 1000)
    }

    private fun vibrate(view: View, nextX: Float, nextY: Float, shipNum: Int) {

        view.animate()
            .translationX(nextX)
            .translationY(nextY)
            .setDuration(((8..12).random() * 1000).toLong())
            .withEndAction {
                //TODO handle remove shark when clicked
                spawnShark.removeView(view)
                totalScore -= (pointPerShip * 0.2).toInt()
                scoreView.text = totalScore.toString()


                if (spawnShark.childCount < 3) {
                    drawShark(shipNum)
                }

                Log.i("sharkNum vibrate", sharkNum.toString())

                if (totalScore < 0) {
                    //TODO handle lose
                    resetGame()
                }
            }
            .start()
    }

    private fun drawShark(randomShip: Int) {
        Log.i("sharkNum drawShark", spawnShark.childCount.toString())
        if (spawnShark.childCount < 3) {
            val randomX = (0..1).random()
            val randomY = (0..spawnShark.height).random().toFloat()

            val shark = ImageView(this)
            val param = LinearLayout.LayoutParams(70, 70)

            shark.setBackgroundResource(R.drawable.shark)
            shark.layoutParams = param
            shark.bringToFront()

            if (randomX == 0) {
                shark.x = -70f
            } else {
                shark.x = (spawnShark.width - 16).toFloat()
            }

            shark.y = randomY

            spawnShark.addView(shark)
            listShark.add(shark)

            vibrate(
                shark,
                ((randomShipCol[randomShip] * widthPerGridItem) + (widthPerGridItem / 2 - 35)).toFloat(),
                (randomShipRow[randomShip] * heightPerGridItem + (heightPerGridItem / 2 - 35)).toFloat(),
                randomShip
            )
        }
    }

    private fun handleTimeUp() {
        //TODO handle lose
        val text = "Timeup!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(this, text, duration)
        toast.show()
    }
}