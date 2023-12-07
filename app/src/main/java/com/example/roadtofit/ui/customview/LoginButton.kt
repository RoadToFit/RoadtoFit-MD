package com.example.roadtofit.ui.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.roadtofit.R

class LoginButton: AppCompatButton {

    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private lateinit var txtColor: ColorStateList

    constructor(context: Context) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isEnabled) {
            background = enabledBackground
            setTextColor(txtColor)
        } else {
            background = disabledBackground
        }

        textSize = 16f
        gravity = Gravity.CENTER
        text = context.getString(R.string.sign_in)
    }

    private fun init() {
        txtColor = ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.background_light))
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.btn_custom) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.btn_custom_disable) as Drawable
    }

}