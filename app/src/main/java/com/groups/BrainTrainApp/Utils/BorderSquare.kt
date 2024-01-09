package com.groups.BrainTrainApp.Utils

import android.graphics.Color
import android.graphics.drawable.Drawable
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

fun removeBorder(view: View) {
    val existingBackground = view.background
    if (existingBackground is LayerDrawable) {
        val layers = arrayOfNulls<Drawable>(existingBackground.numberOfLayers - 1)
        for (i in 0 until existingBackground.numberOfLayers - 1) {
            layers[i] = existingBackground.getDrawable(i)
        }
        val newBackground = LayerDrawable(layers)
        view.background = newBackground
    } else {
        view.background = null
    }
}