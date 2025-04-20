package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.utis.KeyboardUtils
import com.github.kongpf8848.viewworld.utis.LogUtils
import io.github.kongpf8848.commonhelper.ScreenHelper
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 验证码输入框
 */
class VerificationCodeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val TAG = "VerificationCodeEditTex"

    companion object {
        const val DEFAULT_CODE_LENGTH = 6
        const val DEFAULT_CODE_MARGIN = 20f
        const val DEFAULT_CODE_WIDTH = 150
        const val DEFAULT_CODE_HEIGHT = 150
        const val BLINK = 500L
    }

    /**
     *验证码个数
     */
    private var mCodeLength = DEFAULT_CODE_LENGTH


    /**
     * 验证码之间的间隔
     */
    private var mCodeMargin = DEFAULT_CODE_MARGIN

    /**
     * 验证码背景
     */
    private var mCodeBackground: Drawable? = null

    /**
     * 验证码宽度
     */
    private var mCodeWidth = DEFAULT_CODE_WIDTH

    /**
     * 验证码高度
     */
    private var mCodeHeight = DEFAULT_CODE_HEIGHT

    /**
     * 光标相关
     */
    private var mCursorDrawableRes = 0
    private var mCursorDrawable: Drawable? = null
    private var mBlink: Blink? = null
    private var mCursorVisible = false
    private var mCursorFlag = false

    /**
     * 输入完成监听
     */
    private var inputTextListener: OnInputTextListener? = null

    init {
        Log.d(TAG, "init called")
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeEditText)
        for (i in 0 until typedArray.indexCount) {
            val index = typedArray.getIndex(i)
            if (index == R.styleable.VerificationCodeEditText_codeLength) {
                mCodeLength = typedArray.getInteger(index, DEFAULT_CODE_LENGTH)
            } else if (index == R.styleable.VerificationCodeEditText_codeBackground) {
                mCodeBackground = typedArray.getDrawable(index)
            } else if (index == R.styleable.VerificationCodeEditText_codeMargin) {
                mCodeMargin = typedArray.getDimension(index, DEFAULT_CODE_MARGIN)
            } else if (index == R.styleable.VerificationCodeEditText_codeWidth) {
                mCodeWidth = typedArray.getDimensionPixelSize(index, DEFAULT_CODE_WIDTH)
            } else if (index == R.styleable.VerificationCodeEditText_codeCursorVisible) {
                mCursorVisible = typedArray.getBoolean(index, false)
            } else if (index == R.styleable.VerificationCodeEditText_codeCursorDrawable) {
                mCursorDrawableRes = typedArray.getResourceId(index, 0)
            }
        }
        typedArray.recycle()


        if (mCodeLength <= 0) {
            throw IllegalArgumentException("code length must large than 0!!!")
        }
        if (mCodeBackground == null) {
            throw NullPointerException("code background drawable not allowed to be null!!!")
        }
        if (mCursorVisible) {
            if (mCursorDrawable == null && mCursorDrawableRes == 0) {
                mCursorDrawable = GradientDrawable().apply {
                    setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    setSize(ScreenHelper.dp2px(context, 1f), 0)
                }
            }
        }

        /**
         * 禁用长按事件
         */
        isLongClickable = false
        /**
         * 隐藏EditText自带光标，防止onDraw方法一直被调用
         */
        isCursorVisible = false
        /**
         * 设置输入框最大长度
         */
        setMaxLength(mCodeLength)
        /**
         * 设置背景为透明
         */
        setBackgroundColor(Color.TRANSPARENT)
    }


    override fun onTextContextMenuItem(id: Int): Boolean {
        return false
    }


    /**
     *设置输入框最大长度
     */
    private fun setMaxLength(maxLength: Int) {
        if (maxLength >= 0) {
            filters = arrayOf<InputFilter>(LengthFilter(maxLength))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST) {
            mCodeHeight = mCodeWidth
            val newWidth = mCodeWidth * mCodeLength + (mCodeLength - 1) * mCodeMargin
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(newWidth.toInt(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mCodeHeight, MeasureSpec.EXACTLY)
            )
        } else {
            mCodeWidth = ((widthSize - mCodeMargin * (mCodeLength - 1)) / mCodeLength).toInt()
            mCodeHeight = mCodeWidth
            setMeasuredDimension(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(mCodeHeight, MeasureSpec.EXACTLY)
            )
        }

    }

    override fun onDraw(canvas: Canvas) {
        LogUtils.d(
            TAG,
            "onDraw:${SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Date())},cursor:${isCursorVisible}"
        )
        drawBackground(canvas)
        drawText(canvas)
        drawCursor(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        resumeBlink()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        suspendBlink()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        Log.d(
            TAG,
            "onTextChanged() called with: text = $text, start = $start, lengthBefore = $lengthBefore, lengthAfter = $lengthAfter"
        )
        text?.apply {
            if (length >= mCodeLength) {
                suspendBlink()
                KeyboardUtils.hideSoftInput(this@VerificationCodeEditText)
                inputTextListener?.onInputTextComplete(this)
            } else if (length + 1 == mCodeLength && lengthBefore == 1) {
                resumeBlink()
            }
        }
    }


    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            mBlink?.uncancel()
            makeBlink()
        } else {
            mBlink?.cancel()
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            makeBlink()
        }
    }

    /**
     * 绘制背景
     */
    private fun drawBackground(canvas: Canvas) {
        mCodeBackground?.run {
            val currentIndex = 0.coerceAtLeast(editableText.length)
            val count = canvas.save()
            for (i in 0 until mCodeLength) {
                bounds = Rect(0, 0, mCodeWidth, mCodeHeight)
                if (currentIndex == i) {
                    state = intArrayOf(android.R.attr.state_selected)
                } else {
                    state = intArrayOf(android.R.attr.state_enabled)
                }
                draw(canvas)
                canvas.translate(mCodeWidth + mCodeMargin, 0f)
            }
            canvas.restoreToCount(count)
        }

    }

    /**
     * 绘制文本
     */
    private fun drawText(canvas: Canvas) {
        val count = canvas.save()
        canvas.translate(0f, 0f)
        val textColor = currentTextColor
        for (i in editableText.indices) {
            val textWidth = paint.measureText(editableText[i].toString())
            val fontMetrics = Paint.FontMetrics()
            paint.getFontMetrics(fontMetrics)
            paint.color = textColor
            val x = (mCodeWidth + mCodeMargin) * i + mCodeWidth / 2f - textWidth / 2f
            val y = mCodeHeight / 2f - (fontMetrics.top + fontMetrics.bottom) / 2f
            canvas.drawText(editableText[i].toString(), x, y, paint)
        }
        canvas.restoreToCount(count)
    }

    /**
     * 绘制光标
     */
    private fun drawCursor(canvas: Canvas) {
        if (!mCursorVisible) return
        mCursorFlag = !mCursorFlag
        if (mCursorFlag) {
            if (mCursorDrawable == null && mCursorDrawableRes != 0) {
                mCursorDrawable = context.getDrawable(mCursorDrawableRes)
            }
            mCursorDrawable?.apply {
                val currentIndex = 0.coerceAtLeast(editableText.length)
                val count = canvas.save()
                val line = layout.getLineForOffset(selectionStart)
                val top = layout.getLineTop(line)
                val bottom = layout.getLineBottom(line)
                val mTempRect = Rect()
                getPadding(mTempRect)
                bounds = Rect(0, top - mTempRect.top, intrinsicWidth, bottom + mTempRect.bottom)
                canvas.translate(
                    (mCodeWidth + mCodeMargin) * currentIndex + mCodeWidth / 2f - intrinsicWidth / 2f,
                    (mCodeHeight - bounds.height()) / 2f
                )
                draw(canvas)
                canvas.restoreToCount(count)
            }
        }
    }


    private fun suspendBlink() {
        mBlink?.cancel()
    }

    private fun resumeBlink() {
        if (mBlink != null) {
            mBlink?.uncancel()
            makeBlink()
        }
    }

    private fun makeBlink() {
        if (shouldBlink()) {
            if (mBlink == null) mBlink = Blink()
            removeCallbacks(mBlink)
            postDelayed(mBlink, BLINK)
        } else {
            if (mBlink != null) removeCallbacks(mBlink)
        }
    }

    private fun shouldBlink(): Boolean {
        if (!mCursorVisible || !isFocused) return false
        val start: Int = selectionStart
        if (start < 0) return false
        val end: Int = selectionEnd
        return if (end < 0) false else start == end
    }

    inner class Blink : Runnable {
        private var mCancelled = false
        override fun run() {
            if (mCancelled) {
                return
            }
            removeCallbacks(this)
            if (shouldBlink()) {
                if (layout != null) {
                    invalidate()
                }
                postDelayed(this, BLINK)
            }
        }

        fun cancel() {
            if (!mCancelled) {
                removeCallbacks(this)
                mCancelled = true
            }
        }

        fun uncancel() {
            mCancelled = false
        }
    }

    fun setOnInputTextListener(listener: OnInputTextListener) {
        this.inputTextListener = listener
    }

    interface OnInputTextListener {
        fun onInputTextComplete(text: CharSequence)
    }

}