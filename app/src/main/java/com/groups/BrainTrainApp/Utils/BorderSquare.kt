package com.groups.BrainTrainApp.Utils

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View

fun <T : View> borderView(view: T, color: Int) {
    val existingBackground = view.background
    val border = GradientDrawable()
    border.setStroke(10, color)
    val layers = arrayOf(existingBackground, border)
    val layerDrawable = LayerDrawable(layers)
    border.cornerRadius = 8f
    view.background = layerDrawable
}