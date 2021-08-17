package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.kongpf8848.viewworld.R


class AudioWaveProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        val TAG = AudioWaveProgressView::class.java.simpleName
        const val MAX_NUM_OF_LEVELS = 100
    }

    private var duration: Long = 0
    private var currentHead: Long = 0
    private var activePaint: Paint? = null
    private var inactivePaint: Paint? = null
    private var levels: FloatArray?=null
    private var binSpaceWidth = 0f

   init{
       val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AudioWaveProgressView)
       val mActiveColor = typedArray.getColor(R.styleable.AudioWaveProgressView_active_color, Color.BLUE)
       val mInactiveColor = typedArray.getColor(R.styleable.AudioWaveProgressView_inactivie_color, Color.GRAY)
       binSpaceWidth= typedArray.getDimension(R.styleable.AudioWaveProgressView_space_width, 2f)

       typedArray.recycle()

       activePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
           color=mActiveColor
       }
       inactivePaint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
           color=mInactiveColor
       }
   }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (levels == null || duration<0L) {
            return
        }
        val inactiveWidth = width - (MAX_NUM_OF_LEVELS - 1) * binSpaceWidth
        val binWidth = inactiveWidth / MAX_NUM_OF_LEVELS
        var currentX = 0f
        val breakPoint = (MAX_NUM_OF_LEVELS * currentHead * 1.0f / duration).toInt()
        for (i in 0 until breakPoint) {
            if (i > MAX_NUM_OF_LEVELS - 1) {
                return
            }
            var lh = levels!![i] * height
            if (lh < binWidth) {
                lh = binWidth
            }
            val top = (height - lh) / 2
            canvas.drawRect(currentX, top, currentX + binWidth, top + lh, activePaint!!)
            currentX += binWidth + binSpaceWidth
        }
        for (i in breakPoint until MAX_NUM_OF_LEVELS) {
            var lh = levels!![i] * height
            if (lh < binWidth) {
                lh = binWidth
            }
            val top = (height - lh) / 2
            canvas.drawRect(currentX, top, currentX + binWidth, top + lh, inactivePaint!!)
            currentX += binWidth + binSpaceWidth
        }
    }


    fun setLevels(levels: FloatArray?) {
        this.levels = levels
        invalidate()
    }

    fun onUpdateProgress(current: Long, total: Long) {
        currentHead = current
        duration = total
        invalidate()
    }


    fun onResetProgress() {
        currentHead = 0
        invalidate()
    }


}