package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View

class CheckColorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val defaultWidth = 120
    private val defaultHeight = 120

    private val bgPaint:Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private var bgColor:String?=""

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = getSize(defaultWidth, widthMeasureSpec)
        var height = getSize(defaultHeight, heightMeasureSpec)
        if (width < height) {
            height = width
        } else {
            width = height
        }
        setMeasuredDimension(width, height)
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
    }

    private fun drawCircle(canvas: Canvas){
        val radius=(width / 2).toFloat()
        if(!TextUtils.isEmpty(bgColor)){
            bgPaint.color=Color.parseColor(this.bgColor)
            canvas.drawCircle(radius,radius,radius, bgPaint)
        }
    }


    private fun getSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.UNSPECIFIED -> {
                result = defaultSize
            }
            MeasureSpec.AT_MOST -> {
                result = size
            }
            MeasureSpec.EXACTLY -> {
                result = size
            }
        }
        return result
    }

    fun setColor(colorString:String){
        this.bgColor = colorString
        invalidate()
    }

}