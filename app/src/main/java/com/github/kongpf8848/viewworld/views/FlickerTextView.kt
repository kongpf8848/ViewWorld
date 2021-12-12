package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.github.kongpf8848.viewworld.R

/**
 * 仿掘金Loading的闪烁TextView
 */
class FlickerTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var flickPaint: Paint
    private var flickColor = 0
    private var flickPercent = 0f
    private var clipLeft = -OFFSET
    private val path = Path()

    companion object {
        const val TAG = "FlickerTextView"
        const val OFFSET = 40f
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FlickerTextView)
        try {
            flickColor = ta.getColor(R.styleable.FlickerTextView_flick_color, Color.parseColor("#DCDCDC"))
            flickPercent = ta.getFloat(R.styleable.FlickerTextView_flick_percent, 0.16f)
        } finally {
            ta.recycle()
        }

        flickPaint = Paint().apply {
            isAntiAlias = true
            textSize = this@FlickerTextView.textSize
            color = flickColor
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw() called with: canvas = $canvas,isVisible:"+isVisible)
        if (isVisible) {
            //裁减平行四边形
            path.reset()
            path.moveTo(clipLeft, 0f)
            path.lineTo(clipLeft + width * flickPercent, 0f)
            path.lineTo(clipLeft + width * flickPercent + OFFSET, height.toFloat())
            path.lineTo(clipLeft + OFFSET, height.toFloat())
            canvas.clipPath(path)
            val fm = flickPaint.fontMetricsInt
            val baseLine = height / 2f - fm.top / 2f - fm.bottom / 2f
            canvas.drawText(text.toString(), 0f, baseLine, flickPaint)

            clipLeft += 5f
            if (clipLeft > width) {
                clipLeft = -OFFSET
            }
            invalidate()
        }

    }
}