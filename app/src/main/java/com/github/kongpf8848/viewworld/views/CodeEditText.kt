package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.utis.KeyboardUtils
import com.github.kongpf8848.viewworld.utis.LogUtils
import com.kongpf.commonhelper.ScreenHelper

/**
 * 验证码输入框
 */
class CodeEditText@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    // 边框个数
    private var mStrokeLength = 6

    // 边框宽度
    private var mStrokeWidth = 0f

    // 边框高度
    private var mStrokeHeight = 0f

    // 边框之间的距离
    private var mStrokePadding = 20f

    // 边框的背景
    private var mStrokeDrawable: Drawable? = null


    /**
     * 输入结束监听
     */
    private var mOnInputFinishListener: OnTextFinishListener? = null


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeEditText)
        val indexCount = typedArray.indexCount
        for (i in 0 until indexCount) {
            val index = typedArray.getIndex(i)
            if (index == R.styleable.CodeEditText_strokeWidth) {
                mStrokeWidth = typedArray.getDimension(index, ScreenHelper.dp2px(context,40f).toFloat())
            }
            else if (index == R.styleable.CodeEditText_strokePadding) {
                mStrokePadding = typedArray.getDimension(index, ScreenHelper.dp2px(context,10f).toFloat())
            } else if (index == R.styleable.CodeEditText_strokeBackground) {
                mStrokeDrawable = typedArray.getDrawable(index)
            } else if (index == R.styleable.CodeEditText_strokeLength) {
                mStrokeLength = typedArray.getInteger(index, 4)
            }
        }
        typedArray.recycle()

        if (mStrokeDrawable == null) {
            throw NullPointerException("stroke drawable not allowed to be null!")
        }

        isLongClickable = false
        isCursorVisible = false
        setMaxLength(mStrokeLength)
        setBackgroundColor(Color.TRANSPARENT)
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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode=MeasureSpec.getMode(widthMeasureSpec)
        val widthSize=MeasureSpec.getSize(widthMeasureSpec)

        if(widthMode==MeasureSpec.AT_MOST){
            if(mStrokeWidth==0f){
                mStrokeWidth=ScreenHelper.dp2px(context,40f).toFloat()
            }
            mStrokeHeight=mStrokeWidth
            val newWidth=mStrokeWidth*mStrokeLength+(mStrokeLength-1)*mStrokePadding
            val xx=newWidth.toInt()
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(newWidth.toInt(),MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mStrokeHeight.toInt(),MeasureSpec.EXACTLY))
        }
        else{
            mStrokeWidth=(widthSize-mStrokePadding*(mStrokeLength-1))/mStrokeLength
            mStrokeHeight=mStrokeWidth
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mStrokeHeight.toInt(),MeasureSpec.EXACTLY))
        }

        LogUtils.d("JACK8","onMeasure:StrokeWidth:${mStrokeWidth}")

    }

    override fun onDraw(canvas: Canvas) {
        LogUtils.d("JACK8","CodeEditText onDraw")
        drawStrokeBackground(canvas)
        drawText(canvas)
    }

    /**
     * 重绘背景
     */
    private fun drawStrokeBackground(canvas: Canvas) {

        val rect=Rect(0,0,mStrokeWidth.toInt(),mStrokeHeight.toInt())
        val activatedIndex = Math.max(0, editableText.length)
        val count = canvas.save()
        for (i in 0 until mStrokeLength) {
            mStrokeDrawable!!.bounds = rect
            if(activatedIndex==i){
                mStrokeDrawable!!.state = intArrayOf(android.R.attr.state_selected)
            }
            else {
                mStrokeDrawable!!.state = intArrayOf(android.R.attr.state_enabled)
            }
            mStrokeDrawable!!.draw(canvas)
            canvas.translate(mStrokeWidth + mStrokePadding, 0f)
        }
        canvas.restoreToCount(count)

    }

    /**
     * 重绘文本
     */
    private fun drawText(canvas: Canvas) {
        val count=canvas.save()
        canvas.translate(0f, 0f)
        val textColor=currentTextColor
        val length = editableText.length
        for (i in 0 until length) {
            val textWidth=paint.measureText(editableText[i].toString())
            val fontMetrics=Paint.FontMetrics()
            paint.getFontMetrics(fontMetrics)
            paint.color=textColor
            val x =  (mStrokeWidth + mStrokePadding) * i + mStrokeWidth / 2f - textWidth/2f
            val y = mStrokeHeight/2f- (fontMetrics.top + fontMetrics.bottom) / 2f
            canvas.drawText(editableText[i].toString(), x, y, paint)
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
        if (textLength == mStrokeLength) {
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