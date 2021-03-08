package com.github.kongpf8848.viewworld.drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View

class DrawableView (context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val drawable = ColorDrawable(Color.RED)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(50, 50, width, height)
        drawable.draw(canvas)

    }
}

