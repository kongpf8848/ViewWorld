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

/**
 * 圆点指示符，带切换动画效果
 */
class CircleIndicator @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private val TAG = "CircleIndicator"

    private var mIndicatorColor = Color.GRAY
    private var mIndicatorSelectedColor = Color.WHITE
    private var mIndicatorWidth = 0f
    private var mIndicatorHeight = 0f
    private var mIndicatorMargin = 0f
    private var mIndicatorCount = 0
    private var mShowAnimation=true
    private var mIndicatorPaint: Paint = Paint()
    private var mViewPager:ViewPager?=null

    private var position = 0
    private var positionOffset = 0f

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator)
        mIndicatorColor = typedArray.getColor(R.styleable.CircleIndicator_ci_IndicatorColor, Color.GRAY)
        mIndicatorSelectedColor = typedArray.getColor(R.styleable.CircleIndicator_ci_IndicatorSelectedColor, Color.WHITE)
        mIndicatorWidth = typedArray.getDimension(R.styleable.CircleIndicator_ci_IndicatorWidth, dp2px(5f))
        mIndicatorHeight = typedArray.getDimension(R.styleable.CircleIndicator_ci_IndicatorHeight, dp2px(5f))
        mIndicatorMargin = typedArray.getDimension(R.styleable.CircleIndicator_ci_IndicatorMargin, dp2px(5f))
        mShowAnimation = typedArray.getBoolean(R.styleable.CircleIndicator_ci_IndicatorShowAnimation, true)

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
        if(mIndicatorCount>0) {
            val width = ((mIndicatorCount - 1) * (mIndicatorMargin + mIndicatorWidth) + mIndicatorWidth).toInt()
            setMeasuredDimension(width, mIndicatorHeight.toInt())
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mIndicatorCount <= 0) {
            return
        }
        drawCircle(canvas)
        drawIndicator(canvas)

    }

    private fun drawCircle(canvas: Canvas){
        val saveCount=canvas.save()

        var left=0f
        var right=0f
        for (i in 0 until mIndicatorCount) {
            left = i * (mIndicatorWidth + mIndicatorMargin)
            right = left + mIndicatorWidth
            mIndicatorPaint.color = mIndicatorColor
            canvas.drawRoundRect(RectF(left, 0f, right, mIndicatorHeight), mIndicatorHeight / 2, mIndicatorHeight / 2, mIndicatorPaint)
        }

        canvas.restoreToCount(saveCount)
    }

    private fun drawIndicator(canvas: Canvas){
        val saveCount=canvas.save()

        var left=0f
        var right=0f

        mIndicatorPaint.color=mIndicatorSelectedColor

        val tempLeft1=position*(mIndicatorWidth+mIndicatorMargin)
        val tempLeft2=(position+1)*(mIndicatorWidth+mIndicatorMargin)

        val tempRight1=tempLeft1+mIndicatorWidth
        val tempRight2=tempLeft2+mIndicatorWidth
        if(positionOffset<=0.5f){
            left=tempLeft1
            right=tempRight1+(tempRight2-tempRight1)*(2*positionOffset)
        }
        else{
            left=(tempLeft1)+(tempLeft2-tempLeft1)*(2*positionOffset-1)
            right=tempRight2
        }
        canvas.drawRoundRect(
            RectF(left, 0f, right, mIndicatorHeight),
            mIndicatorHeight / 2,
            mIndicatorHeight / 2,
            mIndicatorPaint
        )
        canvas.restoreToCount(saveCount)
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        mViewPager = viewPager
        mViewPager?.apply {
            removeOnPageChangeListener(this@CircleIndicator)
            addOnPageChangeListener(this@CircleIndicator)
            mIndicatorCount=adapter?.count?:0
        }
        requestLayout()
        invalidate()
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(mShowAnimation) {
            this.position = position
            this.positionOffset = positionOffset
            invalidate()
        }
    }

    override fun onPageSelected(position: Int) {
        if(!mShowAnimation) {
            this.position = position
            this.positionOffset = 0f
            invalidate()
        }
    }

    private fun dp2px(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

}