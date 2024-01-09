package com.groups.BrainTrainApp.Utils

import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView

fun handleProgressBar(progressBar: ProgressBar, progressText: TextView, totalTime: Int, handleTimeUp: () -> Unit): Pair<Handler, Runnable> {
    return handleProgressLoop(progressBar, progressText, totalTime, totalTime, handleTimeUp)
}

fun handleProgressLoop(progressBar: ProgressBar, progressText: TextView, totalTime: Int, timeRemain: Int, handleTimeUp: () -> Any)
    : Pair<Handler, Runnable> {

    var remain: Int = timeRemain
    val progressPerTime: Int = 100 / totalTime

    val handler: Handler = Handler(Looper.getMainLooper())
    val runnable = Runnable {
        if (timeRemain > 0) {
            remain--
            progressText.text = remain.toString() + "s"
            progressBar.progress = progressBar.progress - progressPerTime

            handleProgressLoop(progressBar, progressText, totalTime, remain, handleTimeUp)
        } else {
            handleTimeUp()
        }
    }
    handler.postDelayed(runnable, 1000)

    return Pair(handler, runnable)
}
