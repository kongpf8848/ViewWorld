package com.github.kongpf8848.tkbanner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.github.kongpf8848.tkbanner.R

class LineIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private val TAG = "LineIndicator"
    private var mIndicatorColor = Color.GRAY
    private var mIndicatorSelectedColor = Color.WHITE
    private var mIndicatorWidth = 0f
    private var mIndicatorHeight = 0f
    private var mIndicatorCount = 0
    private var mIndicatorPaint: Paint = Paint()

    private var position = 0
    private var positionOffset = 0f

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineIndicator)
        mIndicatorColor = typedArray.getColor(R.styleable.LineIndicator_LineIndicator_Color, Color.GRAY)
        mIndicatorSelectedColor = typedArray.getColor(R.styleable.LineIndicator_LineIndicator_SelectedColor, Color.WHITE)
        mIndicatorWidth = typedArray.getDimension(R.styleable.LineIndicator_LineIndicator_Width, dp2px(5f))
        mIndicatorHeight = typedArray.getDimension(R.styleable.LineIndicator_LineIndicator_Height, dp2px(5f))
        typedArray.recycle()

        mIndicatorPaint.apply {
            isDither = true
            isAntiAlias = true
            style = Paint.Style.FILL
            color = mIndicatorColor
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mIndicatorCount > 0) {
            val width = (mIndicatorWidth * mIndicatorCount).toInt()
            setMeasuredDimension(width, mIndicatorHeight.toInt())
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mIndicatorCount <= 0) {
            return
        }
        val rect=RectF(0f,0f,width.toFloat(),mIndicatorHeight)
        mIndicatorPaint.color = mIndicatorColor
        canvas.drawRect(rect, mIndicatorPaint)

        mIndicatorPaint.color=mIndicatorSelectedColor
        canvas.translate((mIndicatorWidth)*(position+positionOffset),0f)
        val rect2=RectF(0f,0f,mIndicatorWidth,mIndicatorHeight)
        canvas.drawRect(rect2,mIndicatorPaint)
    }

    fun setUp(count: Int) {
        mIndicatorCount = count
        requestLayout()
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        this.position = position
        this.positionOffset = positionOffset
        invalidate()
    }

    override fun onPageSelected(position: Int) {
    }

    private fun dp2px(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

}