package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.github.kongpf8848.viewworld.R

/**
 * 圆弧背景
 */
class ArcView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private val mArcHeight: Int
    private val mStartColor: Int
    private val mEndColor: Int
    private val mPaint: Paint
    private var linearGradient: LinearGradient? = null
    private val rect: Rect = Rect(0, 0, 0, 0)
    private val path: Path = Path()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView)
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arc_height, 0)
        mStartColor = typedArray.getColor(R.styleable.ArcView_arc_start_color, Color.RED)
        mEndColor = typedArray.getColor(R.styleable.ArcView_arc_end_color, Color.RED)

        mPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        typedArray.recycle()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth=w
        mHeight=h
        linearGradient = LinearGradient(0f, 0f, mWidth.toFloat(), 0f, mStartColor, mEndColor, Shader.TileMode.CLAMP)
        mPaint.shader = linearGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制矩形
        rect.set(0, 0, mWidth, mHeight - mArcHeight)
        canvas.drawRect(rect, mPaint)

        //绘制圆弧
        path.moveTo(0f, (mHeight - mArcHeight).toFloat())
        path.quadTo((mWidth shr 1).toFloat(), mHeight.toFloat(), mWidth.toFloat(), (mHeight - mArcHeight).toFloat())
        canvas.drawPath(path, mPaint)

    }


}