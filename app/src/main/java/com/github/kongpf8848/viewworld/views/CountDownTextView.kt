package com.github.kongpf8848.viewworld.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import io.github.kongpf8848.commonhelper.ScreenHelper

/**
 * 倒计时文本
 */
class CountDownTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val duration = 3000L
    private var mSweepAngle = 360f
    private var animator: ValueAnimator? = null
    private val mRect = RectF()
    private var mBackgroundPaint: Paint? = null

    var callback: (() -> Unit)? = null

    init {
        mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isAntiAlias = true
            color = Color.WHITE
            strokeWidth = ScreenHelper.dp2px(context, 2f).toFloat()
            style = Paint.Style.STROKE
        }
    }

    /**
     * 开始倒计时
     */
    fun start() {
        if (mSweepAngle != 360f) return
        animator = ValueAnimator.ofFloat(mSweepAngle).setDuration(duration).apply {
            addUpdateListener { animation ->
                mSweepAngle = animation.animatedValue as Float
                invalidate()
            }
        }
        animator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {
            }

            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                callback?.invoke()
            }

            override fun onAnimationCancel(animation: Animator) {
            }


        })
        animator?.start()
    }

    /**
     * 结束倒计时
     */
    fun stop() {
        if (animator != null) {
            animator!!.cancel()
            animator = null
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = ScreenHelper.dp2px(context, 4f)
        mRect.set(
            padding.toFloat(),
            padding.toFloat(),
            width - padding.toFloat(),
            height - padding.toFloat()
        )

        canvas.drawArc(
            mRect, -90f,
            mSweepAngle,
            false,
            mBackgroundPaint!!
        )
    }


}