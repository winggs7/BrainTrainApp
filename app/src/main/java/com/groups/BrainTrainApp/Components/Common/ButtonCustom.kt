package com.groups.BrainTrainApp.Components.Common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton

class ButtonCustom : AppCompatImageButton {
    var isChoose: Boolean = false
    var backgroundResourceId: Int? = null

    constructor(context: Context) : super(context) {

        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

        init()
    }

    private fun init() {

    }
}