package com.groups.BrainTrainApp.Components.Common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton

class ButtonCustom : AppCompatButton  {
    var isChoose: Boolean = false
    var isMark: Boolean = false
    var backgroundResourceId: Int? = null
    var marginStart: Int = 10
    var marginTop: Int = 10
    var marginEnd: Int = 10
    var marginBottom: Int = 10
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
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(marginStart, marginTop, marginEnd, marginBottom)
        this.layoutParams = layoutParams
        this.requestLayout()
    }

}