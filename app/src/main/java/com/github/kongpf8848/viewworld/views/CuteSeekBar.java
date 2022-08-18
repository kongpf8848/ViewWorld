package com.github.kongpf8848.viewworld.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.github.kongpf8848.viewworld.R;

public class CuteSeekBar extends AppCompatSeekBar {

    private static final String TAG = "MySeekBar";

    private int mIndicatorColor;
    private int mIndicatorSelectedColor;
    private float mIndicatorWidth;
    private float mIndicatorHeight;
    private float mIndicatorMargin;

    private Paint mPaint;
    private int mIndicatorCount = 0;
    private int mWidth;
    private int mHeight;


    public CuteSeekBar(@NonNull Context context) {
        this(context,null);
    }

    public CuteSeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CuteSeekBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CuteSeekBar);
        mIndicatorColor = typedArray.getColor(R.styleable.CuteSeekBar_IndicatorColor, Color.GRAY);
        mIndicatorSelectedColor = typedArray.getColor(R.styleable.CuteSeekBar_IndicatorSelectedColor, Color.BLUE);
        mIndicatorWidth = typedArray.getDimension(R.styleable.CuteSeekBar_IndicatorWidth, 30f);
        mIndicatorHeight = typedArray.getDimension(R.styleable.CuteSeekBar_IndicatorHeight, 10f);
        mIndicatorMargin = typedArray.getDimension(R.styleable.CuteSeekBar_IndicatorMargin,10);

        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mIndicatorColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mIndicatorCount = (int)((mWidth - getPaddingLeft() - getPaddingRight()+ mIndicatorMargin) / (mIndicatorWidth+mIndicatorMargin));

        Log.d(TAG, "onDraw() called with: mWidth:" + mWidth + ",mHeight:" + mHeight);
        Log.d(TAG, "onDraw() called with: getPaddingLeft():" + getPaddingLeft()
                + ",getPaddingRight():" + getPaddingRight()
                + ",getPaddingTop:" + getPaddingTop()
                + ",getPaddingBottom:" + getPaddingBottom());
        Log.d(TAG, "onDraw() called with: count = [" + mIndicatorCount + "]");
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getPaddingLeft(), mHeight / 2f-mIndicatorHeight/2f);
        float left,right;
        for (int i = 0; i < mIndicatorCount; i++) {
            left = i * (mIndicatorWidth + mIndicatorMargin);
            right = left + mIndicatorWidth;
            if (right > (mWidth -getPaddingLeft()- getPaddingRight())) {
                right = mWidth -getPaddingLeft()-getPaddingRight();
            }
            float position = getProgress() * 1.0f / (getMax()) * (mWidth - getPaddingLeft() - getPaddingRight());
            if (left >= position) {
                mPaint.setColor(mIndicatorColor);
            } else {
                mPaint.setColor(mIndicatorSelectedColor);
            }
            canvas.drawRoundRect(left, 0, right, mIndicatorHeight, mIndicatorHeight / 2f, mIndicatorHeight / 2f, mPaint);
        }
        canvas.restore();

        super.onDraw(canvas);

    }
}
