package com.groups.BrainTrainApp.Memory

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MyButton : AppCompatButton {
    var isChoose: Boolean = false

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