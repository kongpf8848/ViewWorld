package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.utis.KeyboardUtils

/**
 * 验证码输入框
 */
class CodeEditText@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    // 输入的最大长度
    private var mMaxLength = 4

    // 边框宽度
    private var mStrokeWidth = 0

    // 边框高度
    private var mStrokeHeight = 0

    // 边框之间的距离
    private var mStrokePadding = 20

    // 方框的背景
    private var mStrokeDrawable: Drawable? = null

    private val mRect = Rect()

    /**
     * 输入结束监听
     */
    private var mOnInputFinishListener: OnTextFinishListener? = null


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeEditText)
        val indexCount = typedArray.indexCount
        for (i in 0 until indexCount) {
            val index = typedArray.getIndex(i)
            if (index == R.styleable.CodeEditText_strokeHeight) {
                mStrokeHeight = typedArray.getDimension(index, 60f).toInt()
            } else if (index == R.styleable.CodeEditText_strokeWidth) {
                mStrokeWidth = typedArray.getDimension(index, 60f).toInt()
            } else if (index == R.styleable.CodeEditText_strokePadding) {
                mStrokePadding = typedArray.getDimension(index, 20f).toInt()
            } else if (index == R.styleable.CodeEditText_strokeBackground) {
                mStrokeDrawable = typedArray.getDrawable(index)
            } else if (index == R.styleable.CodeEditText_strokeLength) {
                mMaxLength = typedArray.getInteger(index, 4)
            }
        }
        typedArray.recycle()

        if (mStrokeDrawable == null) {
            throw NullPointerException("stroke drawable not allowed to be null!")
        }
        setMaxLength(mMaxLength)
        isLongClickable = false
        setBackgroundColor(Color.TRANSPARENT)
        isCursorVisible = false
    }


    override fun onTextContextMenuItem(id: Int): Boolean {
        return false
    }


    private fun setMaxLength(maxLength: Int) {
        filters = if (maxLength >= 0) {
            arrayOf<InputFilter>(LengthFilter(maxLength))
        } else {
            arrayOfNulls(0)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (height < mStrokeHeight) {
            height = mStrokeHeight
        }
        val recommendWidth = mStrokeWidth * mMaxLength + mStrokePadding * (mMaxLength - 1)
        if (width < recommendWidth) {
            width = recommendWidth
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode)
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        drawStrokeBackground(canvas)
        drawText(canvas)
    }

    /**
     * 重绘背景
     */
    private fun drawStrokeBackground(canvas: Canvas) {
        mRect.left = 0
        mRect.top = 0
        mRect.right = mStrokeWidth
        mRect.bottom = mStrokeHeight
        val count = canvas.saveCount
        canvas.save()
        for (i in 0 until mMaxLength) {
            mStrokeDrawable!!.bounds = mRect
            mStrokeDrawable!!.state = intArrayOf(android.R.attr.state_enabled)
            mStrokeDrawable!!.draw(canvas)
            val dx = mRect.right + mStrokePadding.toFloat()
            canvas.save()
            canvas.translate(dx, 0f)
        }
        canvas.restoreToCount(count)
        canvas.translate(0f, 0f)

        // 绘制激活状态的边框
        // 当前激活的索引
        val activatedIndex = Math.max(0, editableText.length)
        mRect.left = mStrokeWidth * activatedIndex + mStrokePadding * activatedIndex
        mRect.right = mRect.left + mStrokeWidth
        mStrokeDrawable!!.state = intArrayOf(android.R.attr.state_selected)
        mStrokeDrawable!!.bounds = mRect
        mStrokeDrawable!!.draw(canvas)
    }

    /**
     * 重绘文本
     */
    private fun drawText(canvas: Canvas) {
        val count = canvas.saveCount
        canvas.translate(0f, 0f)
        val length = editableText.length
        for (i in 0 until length) {
            val text = editableText[i].toString()
            val textPaint = paint
            textPaint.color = currentTextColor
            textPaint.getTextBounds(text, 0, 1, mRect)
            val x = mStrokeWidth / 2 + (mStrokeWidth + mStrokePadding) * i - mRect.centerX()
            val fontMetrics=Paint.FontMetrics()
            textPaint.getFontMetrics(fontMetrics)
            val y = mStrokeHeight/2- (fontMetrics.top + fontMetrics.bottom) / 2
            canvas.drawText(text, x.toFloat(), y.toFloat(), textPaint)
        }
        canvas.restoreToCount(count)
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val textLength = text.length
        if (textLength == mMaxLength) {
            KeyboardUtils.hideSoftInput(this)
            mOnInputFinishListener?.onTextFinish(text)
        }
    }

    fun setOnTextFinishListener(onInputFinishListener: OnTextFinishListener?) {
        mOnInputFinishListener = onInputFinishListener
    }

    interface OnTextFinishListener {
        fun onTextFinish(text: CharSequence)
    }

}