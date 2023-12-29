package com.groups.BrainTrainApp.Utils

import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView

fun handleProgressBar(progressBar: ProgressBar, progressText: TextView, totalTime: Int, handleTimeUp: () -> Unit) {
    handleProgressLoop(progressBar, progressText, totalTime, totalTime, handleTimeUp)
}

fun handleProgressLoop(progressBar: ProgressBar, progressText: TextView, totalTime: Int, timeRemain: Int, handleTimeUp: () -> Any) {
    var remain: Int = timeRemain
    val progressPerTime: Int = 100 / totalTime

    Handler(Looper.getMainLooper()).postDelayed({
        if (timeRemain > 0) {
            remain--
            progressText.text = remain.toString() + "s"
            progressBar.progress = progressBar.progress - progressPerTime
            handleProgressLoop(progressBar, progressText, totalTime, remain, handleTimeUp)
        } else {
            handleTimeUp()
        }
    }, 1000)
}
