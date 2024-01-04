package com.groups.BrainTrainApp.Utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable

import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.R

fun borderButton( button: ButtonCustom, color: Int) {
    val existingBackground = button.background
    val border = GradientDrawable()
    border.setStroke(10, color)
    val layers = arrayOf(existingBackground, border)
    val layerDrawable = LayerDrawable(layers)
    border.cornerRadius = 8f
    button.background = layerDrawable
}