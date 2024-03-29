package com.groups.BrainTrainApp.Utils

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import com.groups.BrainTrainApp.Components.Common.ButtonCustom
import com.groups.BrainTrainApp.R

fun drawButton(context: Context, totalLayout: LinearLayout, buttonList: MutableList<ButtonCustom>, numCol: Int): LinearLayout {
    totalLayout.removeAllViews()

    var count = numCol
    var currentRow: LinearLayout? = null
    val a = totalLayout.width

    if (buttonList.size == (count) * (count + 1)) {
        count++
    }

    for (i in buttonList.indices) {
        val button = buttonList[i]

        button.layoutParams = ViewGroup.LayoutParams((a / count)*90/100, (a / count)*90/100)
        if (button.parent != null) {
            (button.parent as? ViewGroup)?.removeView(button)
        }

        if (i % count == 0) {
            currentRow = LinearLayout(context)
            currentRow.orientation = LinearLayout.HORIZONTAL
            currentRow.setHorizontalGravity(1)
            totalLayout.addView(currentRow)
        }
        currentRow?.addView(button)

    }
//    totalLayout.requestLayout()

    return totalLayout
}

fun disableAllButton(buttonList: MutableList<ButtonCustom>) {
    for (i in buttonList.indices) {
        val button = buttonList[i]
        button.isEnabled = false
    }
}

fun enableAllButton(buttonList: MutableList<ButtonCustom>) {
    for (i in buttonList.indices) {
        val button = buttonList[i]
        button.isEnabled = true
    }
}