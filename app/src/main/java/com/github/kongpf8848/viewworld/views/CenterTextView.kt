package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 居中显示的TextView控件
 */
class CenterTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var layout: StaticLayout?=null
    private var textPaint: TextPaint?=null

    private fun initData(){
        this.textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        this.textPaint!!.textSize = textSize
        this.textPaint!!.color = currentTextColor
        this.layout = StaticLayout(
            text,
            this.textPaint,
            width,
            Layout.Alignment.ALIGN_CENTER,
            1.0f,
            0.0f,
            false
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
    }

    override fun onDraw(canvas: Canvas) {
        this.layout?.draw(canvas)
    }



}