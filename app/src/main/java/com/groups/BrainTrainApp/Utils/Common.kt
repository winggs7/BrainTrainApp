package com.groups.BrainTrainApp.Utils

import android.content.Context
import android.content.Intent
import com.groups.BrainTrainApp.Components.Common.GameEnd
import java.util.Locale

fun getResourceId(context: Context, name: String): Int {
    var name = name.lowercase(Locale.getDefault())
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

fun handleEndGame(context: Context, score: Int, time: Int): Intent? {
    val intent = Intent(context, GameEnd::class.java)
    intent.putExtra("score", score.toString())
    intent.putExtra("time", time.toString())
    context.startActivity(intent)
    return null
}