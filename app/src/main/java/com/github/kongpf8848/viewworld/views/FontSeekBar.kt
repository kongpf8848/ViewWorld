package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import com.github.kongpf8848.viewworld.R
import io.github.kongpf8848.commonhelper.ScreenHelper

/**
 * 调整字体的SeekBar
 */
class FontSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatSeekBar(context, attrs, defStyleAttr) {

    private val mTickMarkTitles = arrayOf(
        "A",
        "标准",
        "中",
        "大",
        "A"
    )
    private val mTextSize = arrayOf(
        14,
        16,
        18,
        20,
        24
    )
    private var mBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTitlePaint: Paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var mTitleTextSize = 0f
    private var mOffsetY = 40f
    private var mLineHeight = 0f // 刻度高度
    private var mThumbWidth = 0
    private val mRect = RectF()
    private var mThumbHeight = 0

    //水平直线高度
    private var horizontalLineHeight = 0f

    init {
        horizontalLineHeight = ScreenHelper.dp2px(context, 1f).toFloat()
        mBgPaint.color = ContextCompat.getColor(context, R.color.c_ff333333)
        mBgPaint.style = Paint.Style.FILL


        mOffsetY = ScreenHelper.dp2px(context, mOffsetY).toFloat()
        mLineHeight = ScreenHelper.dp2px(context, 10f).toFloat()
        mTitlePaint.textAlign = Paint.Align.CENTER
        mTitlePaint.color = ContextCompat.getColor(context, R.color.c_ff333333)
        mTitleTextSize = ScreenHelper.dp2px(context, 18f).toFloat()
        mTitlePaint.textSize = mTitleTextSize
    }


    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mThumbWidth = thumb.intrinsicWidth
        mThumbHeight = thumb.intrinsicHeight

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val w = measuredWidth
        var h = measuredHeight
        h += ScreenHelper.dp2px(context, mTextSize[mTextSize.size - 1].toFloat())
        h += mOffsetY.toInt()
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(w, widthMode),
            MeasureSpec.makeMeasureSpec(h, heightMode)
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val max = max
        val halfHeight = height / 2

        //画直线
        mRect.left = paddingLeft.toFloat()
        mRect.right = width - paddingRight.toFloat()
        mRect.top = halfHeight - horizontalLineHeight / 2
        mRect.bottom = halfHeight + horizontalLineHeight / 2
        canvas.drawRect(mRect, mBgPaint)

        val cw = mRect.right - mRect.left
        for (i in 0..max) {
            val center = paddingLeft + cw * i / max
            mRect.top = halfHeight - mLineHeight / 2
            mRect.bottom = halfHeight + mLineHeight / 2
            mRect.left = center - 1.5f
            mRect.right = center + 1.5f
            canvas.drawRect(mRect, mBgPaint)

            // 画刻度文本
            val title = mTickMarkTitles[i % mTickMarkTitles.size]
            mTitlePaint.textSize = ScreenHelper.dp2px(context, mTextSize[i].toFloat()).toFloat()
            canvas.drawText(
                title,
                center,
                ScreenHelper.dp2px(context, mTextSize[mTextSize.size - 1].toFloat()).toFloat(),
                mTitlePaint
            )
        }
    }

    fun getRawTextSize(progress: Int): Int {
        return mTextSize[progress % mTextSize.size]
    }

    fun getTextSize(progress: Int): Int {
        return ScreenHelper.dp2px(context, getRawTextSize(progress).toFloat())
    }

    fun setTextSize(size: Int) {
        for (i in mTextSize.indices) {
            val textSize = ScreenHelper.dp2px(context, mTextSize[i].toFloat())
            if (textSize == size) {
                progress = i
                break
            }
        }
    }
}